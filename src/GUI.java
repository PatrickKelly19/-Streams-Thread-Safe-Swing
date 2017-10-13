import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class GUI implements ActionListener { //Just class GUI extends JFrame dont add action listener

    FileWriter fw = null;
    BufferedWriter bw = null;
    PrintWriter pw = null;

    public static final String Input_File = "C:/Users/jkjke/Documents/big.txt";
    public static final String Output_File = "C:/Users/jkjke/Desktop/big.zip";

    JPanel panelForTextFields, completionPanel;
    JLabel headerLabel, timetextLabel, datetextLabel, comtextLabel, uncomtextLabel, pertextLabel;
    JTextArea textField, timetextField, datetextField, comtextField, uncomtextField, pertextField;
    JButton openButton, zipButton, clearButton;
    JScrollPane scroll;

    Logger logger = Logger.getLogger("MyLog");

    //GUI interface design
    public JPanel createContentPane (){
        // We create a bottom JPanel to place everything on.
        JPanel totalGUI = new JPanel();
        totalGUI.setSize(700,700);
        totalGUI.setLayout(null);

        // TextFields Panel Container
        panelForTextFields = new JPanel();
        panelForTextFields.setLayout(null);
        panelForTextFields.setLocation(600, 10);
        panelForTextFields.setSize(700, 600);
        totalGUI.add(panelForTextFields);

        // Text File Contents Textfield
        textField = new JTextArea();
        textField.setLocation(0, 0);
        textField.setSize(670, 200);

        //Scroll Bar
        scroll = new JScrollPane(textField);
        scroll.setBounds(3,3,500,600);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        panelForTextFields.add(scroll);

        // Header Label
        headerLabel = new JLabel("Application Information");
        headerLabel.setLocation(-30, 210);
        headerLabel.setSize(200, 40);
        headerLabel.setHorizontalAlignment(0);
        panelForTextFields.add(headerLabel);

        // Time To Load Label
        timetextLabel = new JLabel("Time To Load: ");
        timetextLabel.setLocation(-60, 270);
        timetextLabel.setSize(200, 30);
        timetextLabel.setHorizontalAlignment(0);
        panelForTextFields.add(timetextLabel);

        // Time To Load TextField
        timetextField = new JTextArea();
        timetextField.setLocation(100, 270);
        timetextField.setSize(200, 30);
        panelForTextFields.add(timetextField);

        // Date Modified Label
        datetextLabel = new JLabel("Date Modified: ");
        datetextLabel.setLocation(-60, 330);
        datetextLabel.setSize(200, 30);
        datetextLabel.setHorizontalAlignment(0);
        panelForTextFields.add(datetextLabel);

        // Date Modified TextField
        datetextField = new JTextArea();
        datetextField.setLocation(100, 330);
        datetextField.setSize(200, 30);
        panelForTextFields.add(datetextField);

        // UnCompressed File Label
        uncomtextLabel = new JLabel("Org File Size: ");
        uncomtextLabel.setLocation(-60, 390);
        uncomtextLabel.setSize(200, 30);
        uncomtextLabel.setHorizontalAlignment(0);
        panelForTextFields.add(uncomtextLabel);

        // UnCompressed File TextField
        uncomtextField = new JTextArea();
        uncomtextField.setLocation(100, 390);
        uncomtextField.setSize(200, 30);
        panelForTextFields.add(uncomtextField);

        // Compressed File Label
        comtextLabel = new JLabel("Zip File Size: ");
        comtextLabel.setLocation(-60, 450);
        comtextLabel.setSize(200, 30);
        comtextLabel.setHorizontalAlignment(0);
        panelForTextFields.add(comtextLabel);

        // Compressed File TextField
        comtextField = new JTextArea();
        comtextField.setLocation(100, 450);
        comtextField.setSize(200, 30);
        panelForTextFields.add(comtextField);

        // Percentage Label
        pertextLabel = new JLabel("Percentage: ");
        pertextLabel.setLocation(-60, 510);
        pertextLabel.setSize(200, 30);
        pertextLabel.setHorizontalAlignment(0);
        panelForTextFields.add(pertextLabel);

        // Percentage TextField
        pertextField = new JTextArea();
        pertextField.setLocation(100, 510);
        pertextField.setSize(200, 30);
        panelForTextFields.add(pertextField);

        // Button for opening the text file
        openButton = new JButton("Open");
        openButton.setLocation(10, 630);
        openButton.setSize(80, 30);
        openButton.addActionListener(this);
        totalGUI.add(openButton);

        // Button for zipping the text file
        zipButton = new JButton("ZIP");
        zipButton.setLocation(110, 630);
        zipButton.setSize(80, 30);
        zipButton.addActionListener(this);
        totalGUI.add(zipButton);

        // Button for clearing the text fields
        clearButton = new JButton("Clear");
        clearButton.setLocation(210, 630);
        clearButton.setSize(80, 30);
        clearButton.addActionListener(this);
        totalGUI.add(clearButton);

        //Scroll Bar GUI


        totalGUI.add(scroll);
        totalGUI.setOpaque(true);
        textField.setLineWrap(true);
        textField.setWrapStyleWord(true);
        textField.setEditable(false);
        timetextField.setEditable(false);
        datetextField.setEditable(false);
        comtextField.setEditable(false);
        uncomtextField.setEditable(false);
        pertextField.setEditable(false);

        logger.info("GUI has been opened");
        return totalGUI;
    }

    //actionlisteners for the above buttons.
    //here each button press is listened for and the relevant action performed once a press is detected
    public void actionPerformed(ActionEvent e) { //Need to create more than one of these for each button

        if (e.getSource() == openButton) {
            openFile();
        }

        if (e.getSource() == zipButton) {
            zipFile();

        }

        if (e.getSource() == clearButton) {
            clear(); //Clear the text fields

        }
    }

    //Create GUI here
    private static void createAndShowGUI() { //NOT STATIC

        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Client-Server Programming - Patrick Kelly");
        GUI demo = new GUI(); //DOESNT NEED TO BE HERE
        frame.setContentPane(demo.createContentPane()); //No need to call demo

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setVisible(true);
    }

    public void clear() {

        //To clear text fields
        textField.setText(null);
        timetextField.setText(null);
        datetextField.setText(null);
        comtextField.setText(null);
        uncomtextField.setText(null);
        pertextField.setText(null);
        logger.info("Clearing Text Fields");
    }

    public void openFile(){

        SwingWorker<String, Void> worker1 = new SwingWorker<String, Void>() {
            long Start = 0, Stop, Finish;
            @Override
            protected String doInBackground() throws Exception {

                logger.info("Selecting file...");
                //File Chooser
                JFileChooser fi = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                int returnValue = fi.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    Start = System.currentTimeMillis();
                    File selecteddFile = fi.getSelectedFile();
                    logger.info("Selected File Path: " + selecteddFile.getAbsolutePath());

                    try {
                        String s = selecteddFile.getAbsolutePath();
                        FileInputStream fi1 = new FileInputStream(s);
                        DataInputStream di1 = new DataInputStream(fi1);
                        BufferedReader br1 = new BufferedReader(new InputStreamReader(di1));
                        textField.read(br1, null);
                    } catch (IOException io) {
                        logger.info("File could not be found");
                    }
                }

                Stop = System.currentTimeMillis();
                Finish = Stop - Start;
                return "Worked";

            }

            @Override
            protected void done() {
                try {
                    String status = get();
                    timetextField.append(((float) Finish / 1000 + " Seconds"));
                    logger.info("Got time to load file");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };
        worker1.execute();
    }

    public void zipFile(){

        logger.info("Zipped File To Desktop");
        SwingWorker<Float, Void> worker = new SwingWorker<Float, Void>() {

            float fileSizeMB1;
            float fileSizeMB2;
            float Percent1;
            DecimalFormat df = new DecimalFormat("#.##");
            Date modificationTime;

            @Override
            protected Float doInBackground() throws Exception {

                try{
                    //Wrap a FileOutputStream around a zipOutputStream
                    //to store the zip stream to a file. Note that this is
                    //not absolutely necessary
                    String zipFilePath = "C:/Users/jkjke/Desktop/big.zip";
                    JFileChooser fi = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                    int returnValue = fi.showOpenDialog(null);
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        File INPUT_FILE = fi.getSelectedFile();

                        FileOutputStream fileOutputStream = new FileOutputStream(zipFilePath);
                        ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);

                        //a zipentry represents a file entry in the zip archive
                        //we name the zipentry after the original files name
                        ZipEntry ZipEntry = new ZipEntry(INPUT_FILE.getName());
                        zipOutputStream.putNextEntry(ZipEntry);

                        FileInputStream fileInputStream = new FileInputStream(INPUT_FILE);
                        byte[] buf = new byte[1024];
                        int bytesRead;

                        //Read the input file by chucnks of 1024 bytes
                        //and write the read bytes to the zip stream
                        while ((bytesRead = fileInputStream.read(buf)) > 0) {
                            zipOutputStream.write(buf, 0, bytesRead);
                        }

                        modificationTime = new Date(ZipEntry.getTime());

                        File file1 = new File("C:/Users/jkjke/Documents/big.txt");
                        File file2 = new File("C:/Users/jkjke/Desktop/big.zip");

                        //Calculation of file size
                        float fileSizeKB1 = file1.length() / 1024;
                        fileSizeMB1 = fileSizeKB1 / 1024;

                        float fileSizeKB2 = file2.length() / 1024;
                        fileSizeMB2 = fileSizeKB2 / 1024;

                        //close zipentry to store the stream to the file
                        zipOutputStream.closeEntry();

                        zipOutputStream.close();
                        zipOutputStream.close();

                        //Rounding the values to two decimal points
                        df.setRoundingMode(RoundingMode.CEILING);

                        //Calculation of file size percentage saved
                        float Percent = ((fileSizeMB1 - fileSizeMB2) / fileSizeMB1);
                        Percent1 = Percent * 100;
                    }

                }catch (IOException e){
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void done() {

                try {
                    Float status = get();

                    //Appending the information to the individual text fields
                    datetextField.append(""+modificationTime);
                    uncomtextField.append(df.format(fileSizeMB1) + "MB");
                    comtextField.append(df.format(fileSizeMB2) + "MB");
                    pertextField.append(df.format(Percent1) + "%");
                    logger.info("ZIP File Information Displayed In GUI");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }

    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
