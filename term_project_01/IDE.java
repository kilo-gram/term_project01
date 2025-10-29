package term_project_01;

import java.io.*;
import java.util.Scanner;

public class IDE {
    private String Filename;
    private boolean isCompile;
    private String command;
    private String fullPath;
    private String s;
    Scanner scanner = new Scanner(System.in);

    public IDE() {
        Filename = null;
        isCompile = false;
        fullPath = null;
    }

    public void Menu() {
        System.out.println("#######################");
        System.out.println("1. Java File Upload");
        System.out.println("2. Compile");
        System.out.println("3. Run");
        System.out.println("4. Reset");
        System.out.println("5. Compile Error File");
        System.out.println("6. Exit");
        System.out.print("Choice : ");
    }

    public void run() {

        while (true) {
            Menu();
            int chioce = scanner.nextInt();
            scanner.nextLine();

            switch (chioce) {
                case 1: {
                    System.out.println("#######################");
                    System.out.print("자바 파일 이름: ");
                    Filename = scanner.nextLine();
                    if (!Filename.endsWith(".java")) {
                        Filename += ".java";
                        System.out.println("확장자를 추가: " + Filename);
                    }

                    isCompile = false;
                    break;
                }
                case 2: {
                    System.out.println("#######################");
                    if (Filename == null) {
                        System.out.println("파일이 업로드 되지 않음.");
                    } else {
                        File_Compile();
                    }
                    break;

                }
                case 3: {
                    System.out.println("#######################");
                    if (Filename == null) {
                        System.out.println("파일이 업로드 되지 않음. ");
                    } else if (isCompile) {
                        System.out.println("컴파일 에러 - 실행 불가 ");
                    } else {
                        File_run();
                    }
                    break;
                }
                case 4: {
                    System.out.println("#######################");
                    if (Filename == null) {
                        System.out.println("오류, 업로드된 파일이 없음.");
                    } else {
                        Filename = null;
                        isCompile = false;
                        System.out.println("파일 삭제 완료");
                    }
                    break;

                }
                case 5: {
                    System.out.println("#######################");
                    if (Filename == null && isCompile == true) {
                        System.out.println("컴파일 에러 파일 없음.");
                    } else {
                        Error_File();
                    }
                    break;
                }
                case 6: {
                    System.out.println("#######################");
                    System.out.println("프로그램 종료");
                    scanner.close();
                    System.exit(0);
                }
                default: {
                    System.out.println("잘못된 선택입니다. 1-6 사이의 숫자를 입력하세요.");
                    break;
                }
            }

        }
    }

    public void File_Compile() {

        try {
            // cd하고 && javac사이에 있는 건 파일 경로
            System.out.println("컴파일할 .java 파일의 전체 경로를 입력하세요.");

            fullPath = scanner.nextLine().trim();
            command = "cd /d \"" + fullPath + "\" && javac \"" + Filename + "\"";

            ProcessBuilder t = new ProcessBuilder("cmd", "/c", command);
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
                System.out.println("3 compile error occurred –" + Filename + ".error ");
            }

        } catch (IOException e) {
            // TODO: handle exception
            System.err.println("에러! 외부 명령어 실행에 실패.\n" + e.getMessage());
        }
    }

    public void File_run() {
        try {

            // cd하고 && java사이에 있는 건 파일 경로
            command = "cd /d \"" + fullPath + "\" && java \"" + Filename + "\"";
            ProcessBuilder t = new ProcessBuilder("cmd", "/c", command);
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
            System.err.println("에러! 파일 실행 실패\n" + e.getMessage());

        }
    }

    public void Error_File() {
        try {
            String Errorfile = Filename + ".error";
            if (isCompile) {
                System.out.println(Errorfile + "\n");

                try {
                    command = "cd /d \"" + fullPath + "\" && javac \"" + Filename + "\"";
                    ProcessBuilder t = new ProcessBuilder("cmd", "/c", command);
                    Process oProcess = t.start();

                    BufferedReader stdOut = new BufferedReader(new InputStreamReader(oProcess.getInputStream()));
                    BufferedReader stdError = new BufferedReader(new InputStreamReader(oProcess.getErrorStream()));

                    while ((s = stdOut.readLine()) != null) {
                        System.out.println(s);
                    }
                    while ((s = stdError.readLine()) != null) {
                        System.out.println(s);
                    }

                } catch (IOException e) {
                    // TODO: handle exception
                    System.err.println("에러! 외부 명령어 실행에 실패.\n" + e.getMessage());
                }
            } else {
                System.out.println("오류 파일이 존재하지 않습니다.");
            }

        } catch (Exception e) {
            // TODO: handle exception
            System.err.println("에러! 오류 파일 읽기 실패: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        IDE ide = new IDE();
        ide.run();
    }
}
