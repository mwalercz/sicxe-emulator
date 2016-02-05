package sixce.model.simulator;

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
import sicxe.model.simulator.assembler.exceptions.asm.AsmErrors;
import sicxe.model.simulator.assembler.exceptions.parse.ParseErrors;
import sicxe.model.simulator.assembler.listing.ProgramListing;
import sicxe.model.simulator.assembler.objectprogram.ObjectProgram;
import sicxe.model.simulator.commons.exceptions.InvalidAddressException;
import sicxe.model.simulator.commons.exceptions.InvalidFlagsException;
import sicxe.model.simulator.commons.exceptions.NoSuchOpcodeException;
import sicxe.model.simulator.commons.exceptions.OutOfRangeException;
import sicxe.model.simulator.disassembler.Disassembler;
import sicxe.model.simulator.loader.Loader;
import sicxe.model.simulator.machine.Machine;
import sicxe.model.simulator.machine.instruction.Instruction;

import java.io.*;
import java.util.List;

/**
 * Created by maciek on 24/01/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class AssemblerLoaderMachineTest {


    private Machine machine;
    private Disassembler disassembler;
    private static Logger LOG = LoggerFactory.getLogger(AssemblerLoaderMachineTest.class);

    ObjectMapper mapper = new ObjectMapper();
    BufferedReader reader;
    String dir;


    @Before
    public void init() throws FileNotFoundException {
        machine = new Machine();
        disassembler = new Disassembler(machine);
        String rootPath = System.getProperty("user.dir");
        dir = rootPath + File.separator + "test-files";
        String copyAsmDir = dir + File.separator + "copy.asm";
        File file = new File(copyAsmDir);
        reader = new BufferedReader(new FileReader(file));
    }

    @Test
    public void integrationCopyTest() throws AsmErrors, IOException, ParseErrors {
        try {
            Pair<ObjectProgram, ProgramListing> programAndListing = Assembler.assembly(reader);
            ObjectProgram objectProgram = programAndListing.getValue0();
            Loader.load(objectProgram, machine.getMemory());
            int i = 0;
            while (i < 100) {
                machine.process();
                i++;
            }


        } catch (AsmErrors e1) {
            LOG.error("error asm!", e1);
            e1.printStackTrace();
        } catch (ParseErrors e2) {
            LOG.error("error parse", e2);
            e2.printStackTrace();
        } catch (NullPointerException e3) {
            e3.printStackTrace();
        } catch (IndexOutOfBoundsException e4) {
            e4.printStackTrace();
        } catch (InvalidAddressException e) {
            e.printStackTrace();
        } catch (InvalidFlagsException e) {
            e.printStackTrace();
        } catch (OutOfRangeException e) {
            e.printStackTrace();
        } catch (NoSuchOpcodeException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void disassemblerTest() throws AsmErrors, IOException, ParseErrors {
        try {
            Pair<ObjectProgram, ProgramListing> programAndListing = Assembler.assembly(reader);
            ObjectProgram objectProgram = programAndListing.getValue0();
            Loader.load(objectProgram, machine.getMemory());
//            int i = 0;
//            while (i < 100) {
//                machine.process();
//                i++;
//            }
            List<Instruction> instructions = disassembler.safeDisassemble(20);

            mapper.writeValue(new File(dir + File.separator + "copy.disassembly"), instructions);



        } catch (AsmErrors e1) {
            LOG.error("error asm!", e1);
            e1.printStackTrace();
        } catch (ParseErrors e2) {
            LOG.error("error parse", e2);
            e2.printStackTrace();
        } catch (NullPointerException e3) {
            e3.printStackTrace();
        } catch (IndexOutOfBoundsException e4) {
            e4.printStackTrace();
        } catch (InvalidAddressException e) {
            e.printStackTrace();


        }


    }
}
