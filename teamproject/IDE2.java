
// package term_project_01;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;

public class IDE2 extends JFrame {
    private JTextField FilePath;
    private JTextField FilePathSave;
    private JTextArea Editing;
    private JTextArea Result;
    private String File_name = null;
    private Boolean isCompile = false;

    public IDE2() {
        setTitle("Term_Project_2");
        setSize(1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JButton Openbutton = new JButton("Opne");
        JButton Savebutton = new JButton("Save");
        JButton Compilebutton = new JButton("Compile");
        JButton Errorbutton = new JButton("Save Error");
        JButton RunButton = new JButton("Run");
        JButton Deletebutton = new JButton("Delete");
        JButton Clearbutton = new JButton("Clear");

        JPanel topPanel = new JPanel(new BorderLayout());
        FilePath = new JTextField();

        JPanel topButton = new JPanel();
        topButton.add(Openbutton);

        topPanel.add(FilePath, BorderLayout.CENTER);
        topPanel.add(topButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        JPanel nextPanel = new JPanel(new BorderLayout());
        FilePathSave = new JTextField();

        JPanel nextButton = new JPanel();
        nextButton.add(Savebutton);

        nextPanel.add(FilePathSave, BorderLayout.CENTER);
        nextPanel.add(nextButton, BorderLayout.EAST);
        add(nextPanel, BorderLayout.CENTER);

        Editing = new JTextArea();
        Editing.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane editScroll = new JScrollPane(Editing);
        add(editScroll, BorderLayout.CENTER);

        JPanel bottomButtons = new JPanel();

        bottomButtons.add(Compilebutton);
        bottomButtons.add(RunButton);
        bottomButtons.add(Errorbutton);
        bottomButtons.add(Deletebutton);
        bottomButtons.add(Clearbutton);

        Result = new JTextArea(8, 1);
        Result.setEditable(false);
        Result.setFont(new Font("Monospaced", Font.PLAIN, 12));
        Result.setBackground(new Color(245, 245, 245));
        JScrollPane resultScroll = new JScrollPane(Result);

        JPanel south = new JPanel(new BorderLayout());
        south.add(bottomButtons, BorderLayout.NORTH);
        south.add(resultScroll, BorderLayout.SOUTH);
        add(south, BorderLayout.SOUTH);

        Openbutton.addActionListener(FileOpenAction());
        Savebutton.addActionListener(FileSaveAction());
        Compilebutton.addActionListener(FileCompileAction());
        RunButton.addActionListener(FileRunAction());
        Errorbutton.addActionListener(FileErrorAction());
        Deletebutton.addActionListener(FileDeleteAction());
        Clearbutton.addActionListener(FileClearAction());

        setVisible(true);
    }

    private void FileOpenAction() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Java Files (*.java)", "java"));
        int ret = chooser.showOpenDialog(null);
        if (ret == JFileChooser.APPROVE_OPTION) {
            String File_name = chooser.getSelectedFile().getPath();

        }
    }

    private void FileSaveAction() {

    }

    private void FileCompileAction() {
        if (File_name == null) {
            System.out.println("파일이 업로드 되지 않음.");
        } else {
            try {
                String s = null;

                ProcessBuilder t = new ProcessBuilder("cmd", "/c", File_name);
                Process oProcess = t.start();

                BufferedReader stdOut = new BufferedReader(new InputStreamReader(oProcess.getInputStream()));
                BufferedReader stdError = new BufferedReader(new InputStreamReader(oProcess.getErrorStream()));

                while ((s = stdOut.readLine()) != null) {
                    isCompile = true;
                }
                while ((s = stdError.readLine()) != null) {
                    isCompile = true;
                }

                if (!isCompile) {
                    System.out.println("compiled successfully");
                } else {
                    System.out.println("3 compile error occurred –" + File_name + ".error ");
                }

            } catch (IOException e) {
                // TODO: handle exception
                System.err.println("에러! 외부 명령어 실행에 실패.\n" + e.getMessage());
            }
        }

    }

    private void FileRunAction() {
        if (File_name == null) {
            System.out.println("파일이 업로드 되지 않음. ");
        } else if (isCompile) {
            System.out.println("컴파일 에러 - 실행 불가 ");
        } else {
            try {
                String s = null;

                ProcessBuilder t = new ProcessBuilder("cmd", "/c", File_name);
                Process process = t.start();

                BufferedReader stdOut = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                while ((s = stdOut.readLine()) != null) {
                    System.out.println(s);
                }
                while ((s = stdError.readLine()) != null) {
                    System.out.println(s);
                }

                System.out.println("Exit Code: " + process.exitValue());

            } catch (Exception e) {
                // TODO: handle exception
                System.out.println("에러! 파일 실행 실패\n" + e.getMessage());

            }
        }

    }

    private void FileErrorAction() {
        String Errorfile = File_name + ".error";
        if (isCompile) {
            System.out.println(Errorfile + "\n");
        } else {
            System.out.println("오류 파일이 존재하지 않습니다.");
        }

    }

    private void FileDeleteAction() {
        if (File_name == null) {
            System.out.println("오류, 업로드된 파일이 없음.");
        } else {
            File_name = null;
            isCompile = false;
            System.out.println("파일 삭제 완료");
        }
    }

    private void FileClearAction() {
        FilePath.setText("");
        FilePathSave.setText("");
        Editing.setText("");
        Result.setText("");
        File_name = null;
        isCompile = false;
    }

    public static void main(String[] args) {
        new IDE2();
    }

}
