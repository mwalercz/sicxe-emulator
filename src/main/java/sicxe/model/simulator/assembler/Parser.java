package sicxe.model.simulator.assembler;

import org.javatuples.Pair;
import org.springframework.stereotype.Service;
import sicxe.model.simulator.assembler.exceptions.BadLineSyntaxException;
import sicxe.model.simulator.assembler.exceptions.CommentException;
import sicxe.model.simulator.assembler.exceptions.OperandNotAbsoluteException;
import sicxe.model.simulator.assembler.exceptions.OperandNotConstantException;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by maciek on 11/01/16.
 */
@Service
public class Parser {

    public static List<SourceLine> parse(InputStreamReader isReader) throws IOException, BadLineSyntaxException {
        List<SourceLine> sourceLines = new LinkedList<>();
        BufferedReader reader = new BufferedReader(isReader);

        String thisLine;
        while ((thisLine = reader.readLine()) != null) {
            try {
                sourceLines.add(parseLine(thisLine));
            } catch (CommentException e) {
                continue;
            }
        }
        return sourceLines;

    }

    public static SourceLine parseLine(String line) throws CommentException, BadLineSyntaxException{
        List<String> tokens = produceTokenList(line);

        if(tokens.get(0) == ".") throw new CommentException();
        if(tokens.size() == 3) {
            String label = tokens.get(0);
            String instruction = tokens.get(1);
            String operand = tokens.get(2);
            return new SourceLine(label, instruction, operand);
        } else if(tokens.size() == 2){
            String instruction = tokens.get(0);
            String operand = tokens.get(1);
            return new SourceLine(instruction, operand);
        } else if(tokens.size() == 1){
            String instruction = tokens.get(0);
            return new SourceLine(instruction);
        }

        throw  new BadLineSyntaxException(tokens.size());

    }

    public static Pair<Boolean, String> checkForExtended(String mnemonic){
        if(mnemonic.charAt(0) == '+'){
            String instr = mnemonic.substring(1);
            return Pair.with(true, instr);
        } else return Pair.with(false, mnemonic);
    }

    public static String parseConstant(String operand) throws OperandNotConstantException{
        if(operand.matches("C'[a-zA-z]+'")){
            String[] tokens = operand.split("'");
            return tokens[1];
        }
        throw new OperandNotConstantException();
    }

    public static Integer parseAbsolute(String operand) throws OperandNotAbsoluteException {
        if(operand.matches("#[0-9]+")){
            return Integer.parseInt(operand.substring(1));
        }
        throw new OperandNotAbsoluteException();
    }

    private static ArrayList<String> produceTokenList(String line){
        String delimiters = "\t";
        String[] tokens = line.toUpperCase().split(delimiters);
        ArrayList<String> nonEmptyTokens = new ArrayList<>();
        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = tokens[i].trim();
            if(!tokens[i].equals("")){
                nonEmptyTokens.add(tokens[i]);
            }
        }
        return nonEmptyTokens;
    }



}
