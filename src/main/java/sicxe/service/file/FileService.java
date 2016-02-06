package sicxe.service.file;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sicxe.controller.MachineController;
import sicxe.model.domain.AssemblyFile;
import sicxe.model.domain.User;
import sicxe.repository.FileRepository;

import java.io.*;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by maciek on 11/01/16.
 */
@Service
public class FileService {

    private final String bucketName = "sicxe-files";
    private final String secretKey = "w76157Qa4O1Hy9OcO+PdQkwzCW4nzG6cdQgLqJ+P";
    private final String accessKey = "AKIAIIUFVHAFWEE6F3PA";
    private final String asmPath = "/asm/asm_";
    private FileRepository fileRepository;
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(MachineController.class);

    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public String uploadAnonymousAsm(MultipartFile file) throws IOException{
        UUID uuid = UUID.randomUUID();
        String key = asmPath + uuid.toString();
        byte[] bytes = file.getBytes();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        AmazonS3 s3client = new AmazonS3Client(new BasicAWSCredentials(accessKey, secretKey));
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        s3client.putObject(new PutObjectRequest(
                bucketName, key, inputStream, objectMetadata));
        return key;
    }

    public AssemblyFile saveAsm(MultipartFile file, User owner) throws IOException {
        String amazonKey = uploadAnonymousAsm(file);
        AssemblyFile assemblyFile = new AssemblyFile();
        assemblyFile.setAmazonKey(amazonKey);
        assemblyFile.setName(file.getOriginalFilename());
        assemblyFile.setOwner(owner);
        return fileRepository.save(assemblyFile);

    }

    public BufferedReader getFileByAmazonKey(String key){
        AmazonS3 s3Client = new AmazonS3Client(new BasicAWSCredentials(accessKey, secretKey));
        S3Object object = s3Client.getObject(new GetObjectRequest(bucketName, key));
        InputStream is = object.getObjectContent();
        return new BufferedReader(new InputStreamReader(is));
    }

    public BufferedReader getFileById(Long id) throws NoSuchFile {
        Optional<AssemblyFile> file = fileRepository.findOne(id);
        if(file.isPresent()){
            return getFileByAmazonKey(file.get().getAmazonKey());
        } else throw new NoSuchFile();

    }

    public BufferedReader getReader(MultipartFile file) throws IOException {
        return new BufferedReader(
                new InputStreamReader(
                        new ByteArrayInputStream(file.getBytes())));
    }
}
