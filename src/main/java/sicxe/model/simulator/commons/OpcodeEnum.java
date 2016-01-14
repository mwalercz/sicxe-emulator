package sicxe.model.simulator.commons;

/**
 * Created by maciek on 23.10.15.
 */

public enum OpcodeEnum {

    /* SIC */
    // load,store
    LDA(0x00, FormatEnum.F34),
    LDX(0x04, FormatEnum.F34),
    LDL(0x08, FormatEnum.F34),
    STA(0x0C, FormatEnum.F34),
    STX(0x10, FormatEnum.F34),
    STL(0x14, FormatEnum.F34),
    // arithmetic
    ADD(0x18, FormatEnum.F34),
    SUB(0x1C, FormatEnum.F34),
    MUL(0x20, FormatEnum.F34),
    DIV(0x24, FormatEnum.F34),
    COMP(0x28, FormatEnum.F34),
    TIX(0x2C, FormatEnum.F34),
    // jumps
    JEQ(0x30, FormatEnum.F34),
    JGT(0x34, FormatEnum.F34),
    JLT(0x38, FormatEnum.F34),
    J(0x3C, FormatEnum.F34),
    // logic
    AND(0x40, FormatEnum.F34),
    OR(0x44, FormatEnum.F34),
    // jump to subroutine
    JSUB(0x48, FormatEnum.F34),
    RSUB(0x4C, FormatEnum.F34),
    // load and store char
    LDCH(0x50, FormatEnum.F34),
    STCH(0x54, FormatEnum.F34),

    /* SICXE  FormatEnum 3/4 */

    // floating point
    ADDF(0x58, FormatEnum.F34),
    SUBF(0x5C, FormatEnum.F34),
    MULF(0x60, FormatEnum.F34),
    DIVF(0x64, FormatEnum.F34),
    COMPF(0x88, FormatEnum.F34),
    //load,store
    LDB(0x68, FormatEnum.F34),
    LDS(0x6C, FormatEnum.F34),
    LDF(0x70, FormatEnum.F34),
    LDT(0x74, FormatEnum.F34),

    STB(0x78, FormatEnum.F34),
    STS(0x7C, FormatEnum.F34),
    STF(0x80, FormatEnum.F34),
    STT(0x84, FormatEnum.F34),

    LPS(0xD0, FormatEnum.F34),
    STI(0xD4, FormatEnum.F34),
    STSW(0xE8, FormatEnum.F34),
    //devices
    RD(0xD8, FormatEnum.F34),
    WD(0xDC, FormatEnum.F34),
    TD(0xE0, FormatEnum.F34),
    SSK(0xEC, FormatEnum.F34),

    /* SICXE FormatEnum 1 */

    FLOAT(0xC0, FormatEnum.F1),
    FIX(0xC3, FormatEnum.F1),
    NORM(0xC8, FormatEnum.F1),
    SIO(0xF0, FormatEnum.F1),
    HIO(0xF4, FormatEnum.F1),
    TIO(0xF8, FormatEnum.F1),

    /* SICXE FormatEnum 2 */

    ADDR(0x90, FormatEnum.F2),
    SUBR(0x94, FormatEnum.F2),
    MULR(0x98, FormatEnum.F2),
    DIVR(0x93, FormatEnum.F2),
    COMPR(0xA0, FormatEnum.F2),
    SHIFTL(0xA4, FormatEnum.F2),
    SHIFTR(0xA8, FormatEnum.F2),
    RMO(0xAC, FormatEnum.F2),
    SVC(0xB0, FormatEnum.F2),
    CLEAR(0xB4, FormatEnum.F2),
    TIXR(0xB8, FormatEnum.F2);


    public final Integer opcode;
    public final FormatEnum format;

    OpcodeEnum(Integer opcode, FormatEnum format) {
        this.opcode = opcode;
        this.format = format;
    }
}
