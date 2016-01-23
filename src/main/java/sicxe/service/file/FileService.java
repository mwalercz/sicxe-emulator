package sicxe.service.file;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sicxe.controller.MachineController;

import java.io.*;
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
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(MachineController.class);

    public String uploadAsm(MultipartFile file) throws IOException{
        UUID uuid = UUID.randomUUID();
        String key = asmPath + uuid.toString();
        byte[] bytes = file.getBytes();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        AmazonS3 s3client = new AmazonS3Client(new BasicAWSCredentials(accessKey, secretKey));
        s3client.putObject(new PutObjectRequest(
                bucketName, key, inputStream, new ObjectMetadata()));
        return key;
    }

    public BufferedReader getFile(String key){
        AmazonS3 s3Client = new AmazonS3Client(new BasicAWSCredentials(accessKey, secretKey));
        S3Object object = s3Client.getObject(new GetObjectRequest(bucketName, key));
        InputStream is = object.getObjectContent();
        return new BufferedReader(new InputStreamReader(is));
    }
}
