package sixce.model.simulator.assembler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.javatuples.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sicxe.Application;
import sicxe.model.simulator.assembler.Assembler;
import sicxe.model.simulator.assembler.listing.ProgramListing;
import sicxe.model.simulator.assembler.exceptions.asm.AsmErrors;
import sicxe.model.simulator.assembler.exceptions.parse.ParseErrors;
import sicxe.model.simulator.assembler.objectprogram.ObjectProgram;

import java.io.*;

/**
 * Created by maciek on 20/01/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class AssemblerTest {

    ObjectMapper mapper = new ObjectMapper();
    BufferedReader reader;
    String dir;
    private static final Logger LOG = LoggerFactory.getLogger(AssemblerTest.class);


    @Before
    public void init() throws FileNotFoundException {

        String rootPath = System.getProperty("user.dir");
        dir = rootPath + File.separator + "test-files";
        String copyAsmDir = dir + File.separator + "copy.asm";
        File file = new File(copyAsmDir);
        reader = new BufferedReader(new FileReader(file));

    }

    @Test
    public void assemblerTest() throws AsmErrors, IOException, ParseErrors {
        try {
            Pair<ObjectProgram, ProgramListing> programAndListing = Assembler.assembly(reader);
            ObjectProgram objectProgram = programAndListing.getValue0();
            ProgramListing programListing = programAndListing.getValue1();
            mapper.writeValue(new File(dir + File.separator + "copy.json"), objectProgram);
            mapper.writeValue(new File(dir + File.separator + "copy.listing"), programListing);
        } catch (AsmErrors e1){
            LOG.error("error asm!", e1);
            e1.printStackTrace();
        } catch (ParseErrors e2){
            LOG.error("error parse", e2);
            e2.printStackTrace();
        } catch (NullPointerException e3){
            e3.printStackTrace();
        } catch (IndexOutOfBoundsException e4){
            e4.printStackTrace();
        }

    }
}
