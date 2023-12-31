package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CustomClassLoader extends ClassLoader{

    String classPath ;

    public CustomClassLoader(String path, ClassLoader parent){
        super(parent);
        //initializing the path through which any new/updates class file will be added (this is the path to the commands folder)
        Path currRelativePath = Paths.get("");
        classPath = currRelativePath.toAbsolutePath().toString()+ "\\target\\classes\\org\\";
        classPath+=path+"\\";
        System.out.println(path);
    }
    public synchronized Class findClass(String name) throws ClassNotFoundException
    {
        byte [] bytes = getClassData(name);
        if ( bytes != null ){
            Class c = defineClass( null , bytes, 0, bytes.length );
            return c;
        }
        return null;

    }
    //This method loads the byte code from the file using the specified path
    public byte [] getClassData(String name){
        System.out.println("class path: "+ classPath);

        try {
            String classFile = classPath + name.replace('.', '/') + ".class";
            System.out.println(classFile);
            int classSize = Long.valueOf((new File(classFile)).length()).intValue();
            byte[] buf = new byte[classSize];
            FileInputStream fileInput = new FileInputStream(classFile);
            classSize = fileInput.read(buf);
            fileInput.close();
            return buf;
        }catch(IOException e){
            return null;
        }

    }
    @Override
    public Class loadClass(String name) throws ClassNotFoundException {
        if(name.equals("Foo")) {
            return this.findClass(name);
        }
        return super.loadClass(name);
    }

}

