package org.asm;

import aj.org.objectweb.asm.ClassReader;
import aj.org.objectweb.asm.ClassWriter;
import aj.org.objectweb.asm.Opcodes;
import org.posmall.util.ASMAddMthodAdapter;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by USER on 2018-02-06.
 */
public class Main {
    public static void main(String[] args) throws IOException {
//        try {
//            ClassPrinter classPrinter = new ClassPrinter();
//            ClassReader classReader = new ClassReader(HelloWorld.class.getName());
//            classReader.accept(classPrinter, 0);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        /*String c_name = com.tmax.tibero.jdbc.TbPreparedStatement.class.getName();
        ClassWriter cw = new ClassWriter(0);
        ClassReader cr = new ClassReader(c_name);
        ASMAddMthodAdapter cv = new ASMAddMthodAdapter( cw, Opcodes.ACC_PUBLIC, c_name, "field_", "I", "setField", "(I)V");
        cr.accept(cv, 0);
        FileOutputStream stream = new FileOutputStream(c_name+".class");
        stream.write(cw.toByteArray());*/

    }
}
