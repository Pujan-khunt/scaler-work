abstract class Employee {
    String name;
    String id;
}

class FullTimeEmployee extends Employee {
    int ctc;
}

class InternEmployee extends Employee {
    int stipend;
}

class EmployeeRepo {
    EmployeeWriter writer;
    EmployeeFormat format;
    
    void save() {
        int fd = writer.openFile();
        String employeeFormat = format.getFormat();
        writer.writeToFile(fd, employeeFormat);
    }
}

class EmployeeFormat {
    String format;
    
    String getFormat() {
        return "format";
    }
}

class EmployeeWriter {
    String filename;
    
    int openFile() {
        System.out.printf("Opening file %s...\n", filename);
        return 1;
    }
    
    void writeToFile(int fd, String format) {
        System.out.printf("Writing to file descriptor: %d, in format: %s", fd, format);
    }
}