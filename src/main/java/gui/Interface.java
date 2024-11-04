/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import semantic.DirectionTable;
import semantic.Semantic;
import semantic.Symbol;
import syntax.Component;
import syntax.RegexValidator;
import syntax.Syntax;
import syntax.Tokens;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;





/**
 *
 * @author diazj
 */
public class Interface extends javax.swing.JFrame {

    
    //String input;
    RegexValidator validator = new RegexValidator();
    String[] inputList ;
    Tokens tokens = new Tokens();
    //int tokenNumber;
    //int position =-1;
    JFileChooser fileChooser;
    File selectedFile;
    ArrayList<String> lineList = new ArrayList<>();
    int positionLine = 1;
    List<Component> lexList = new ArrayList<>();
    List<Component> lexList2 = new ArrayList<>();
    boolean error = false;
    Syntax syntax;
    Semantic semantic;
    DirectionTable directionTable;
    List<Symbol> symbolList = new ArrayList<>();
    List<DirectionTable> directionTableList = new ArrayList<>();
    //List<String> blancoList = new ArrayList<>();
    
    public Interface() {
        initComponents();
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        titleLbl = new javax.swing.JLabel();
        selectFileBttn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        submitBttn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        titleLbl.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        titleLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLbl.setText("Compiler");

        selectFileBttn.setText("Choose File");
        selectFileBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectFileBttnActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        submitBttn.setText("Submit");
        submitBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitBttnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(titleLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(136, 136, 136)
                        .addComponent(submitBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(222, 222, 222)
                        .addComponent(selectFileBttn))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(87, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(titleLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addComponent(selectFileBttn)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(submitBttn)
                .addContainerGap(145, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void submitBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitBttnActionPerformed
       
            try {
                readFile(selectedFile);
                //System.out.println(Arrays.asList(lineList));
                for(String str : lineList){
                    //positionLine++;
                    inputList = str.split(" ");
                        compile2(inputList, positionLine);
                        writeFile(lexList);
                    positionLine++;
                }
                syntax = new Syntax();
                lexList2.addAll(lexList);
        
                if(syntax.checkSyntax(lexList)){
                  //openFile();

                  openFiles();

                }else{
                    //openFile();
                    semantic = new Semantic();
                    semantic.getClassName(lexList2);
                    semantic.checkVariables(lexList2,symbolList);
                    directionTable = new DirectionTable();
                    directionTable.createDirectionsTable(symbolList,directionTableList);
                    for (Symbol symbol : symbolList){
                        System.out.println(symbol.getId() + " " +symbol.getToken()+
                                " "+symbol.getValue()+ " " +symbol.getContext());
                    }
                    semantic.checkAssignments(lexList2, symbolList);
                    semantic.checkComparison(lexList2,symbolList);
                    writeSymbolsTable(symbolList);
                    writeDirectionsTable(directionTableList);
                    openFiles();
                    this.dispose();
                }
              
            } catch (IOException ex) {
                Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }//GEN-LAST:event_submitBttnActionPerformed

    private void selectFileBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectFileBttnActionPerformed
        chooseFile();
    }//GEN-LAST:event_selectFileBttnActionPerformed

    public void printOutput(String str, int token, int pos){
        System.out.println(str+","+token+","+pos+",1");
    }
    
    public void readFile(File archivo) throws IOException{
        BufferedReader lector = new BufferedReader(new FileReader(archivo));
        String linea;
        while((linea = lector.readLine())!=null){
           lineList.add(linea);
        }
        lector.close();
    }
      
    public void writeFile(List<Component> lista) throws IOException{
        
        PrintWriter pw = new PrintWriter(new FileWriter("C:\\Users\\diazj\\Desktop\\Compiler\\src\\main\\java\\files\\TablaTokens.txt"));
        
            for(Component le : lista){
                //pw.println(Arrays.asList(le.toString()));
                pw.println(le.getLex()+", "+le.getToken()+", "+le.getSymbolPosition()+", "+le.getLine());
                // pw.println(printOutputs(cadena,tokenNumber,position,positionLine));
            }
        pw.close();
        
        
     }

    public void writeSymbolsTable(List<Symbol> list) throws IOException{

        PrintWriter pw = new PrintWriter(new FileWriter("C:\\Users\\diazj\\Desktop\\Compiler\\src\\main\\java\\files\\TablaSimbolos.txt"));

        for(Symbol symbol : list){
            //pw.println(Arrays.asList(le.toString()));
            pw.println(symbol.getId()+", "+symbol.getToken()+", "+symbol.getValue()+", "+
                    symbol.getType()+", "+symbol.getContext());
            // pw.println(printOutputs(cadena,tokenNumber,position,positionLine));
        }
        pw.close();

    }

    public void writeDirectionsTable(List<DirectionTable> list) throws IOException{

        PrintWriter pw = new PrintWriter(new FileWriter("C:\\Users\\diazj\\Desktop\\Compiler\\src\\main\\java\\files\\TablaDirecciones.txt"));

        for(DirectionTable directionTable : list){
            //pw.println(Arrays.asList(le.toString()));
            pw.println(directionTable.getId()+", "+directionTable.getToken()+", "+directionTable.getLine()+", "+
                    directionTable.getVci());
            // pw.println(printOutputs(cadena,tokenNumber,position,positionLine));
        }
        pw.close();


    }
    
    public void openFile(){
        String fileName = "Automatas1.txt";
        try {
            File file = new File(fileName);
            Desktop.getDesktop().open(file); // Abre el archivo con la aplicación predeterminada
        } catch (IOException e) {
            System.out.println("Error al abrir el archivo: " + e.getMessage());
        }
    }

    public void openFiles(){
        String scriptPath = "C:\\Users\\diazj\\Desktop\\Compiler\\src\\main\\java\\files\\script.ps1";  // Ruta a tu script PowerShell
        try {
            Process process = Runtime.getRuntime().exec("powershell.exe -ExecutionPolicy Bypass -File " + scriptPath);
            process.waitFor();  // Espera a que el script termine
        } catch (IOException | InterruptedException e) {
            System.out.println("Error al ejecutar el script: " + e.getMessage());
        }
    }
    
    public void chooseFile(){
        fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(null);
        selectedFile = fileChooser.getSelectedFile();
        jTextArea1.setText(selectedFile.toString());
    }
    
    
    

    public void compile2(String[] list, int ps) {
        Map<Predicate<String>, Function<String, Integer>> tokenMap = new HashMap<>();

        // Mapeo de condiciones con las acciones correspondientes
        tokenMap.put(validator::isRelationalOperator, tokens::relationalTokens);
        tokenMap.put(validator::isSpecialCharacter, tokens::specialCharacterTokens);
        tokenMap.put(validator::isArithmeticOperator, tokens::arithmeticTokens);
        //tokenMap.put(validator::isIdentifier, tokens::identifierTokens);
        tokenMap.put(validator::isReservedWord, tokens::reservedWordTokens);
        tokenMap.put(validator::isLogicalOperator, tokens::logicTokens);
        tokenMap.put(validator::isLogicalValue, tokens::logicalValueTokens);

        Arrays.stream(list).forEach(s -> {
            if (validator.isAComment(s) || s.equals("")) return;  // Si es comentario o vacío, saltarlo

            Component lex = null;

            // Buscar en el mapa la primera condición que sea verdadera
            for (Map.Entry<Predicate<String>, Function<String, Integer>> entry : tokenMap.entrySet()) {
                if (entry.getKey().test(s)) {
                    lex = createComponent(s, entry.getValue().apply(s), ps);
                    break;
                }
            }

            // Condiciones adicionales que no encajan en el mapa
            if (lex == null) {
                if (validator.isConstantString(s)) {
                    lex = createComponent(s, -63, ps);
                } else if (validator.isDecimalNumber(s)) {
                    lex = createComponent(s, -62, ps);
                } else if (validator.isIntegerNumber(s)) {
                    lex = createComponent(s, -61, ps);
                } else if (validator.isIdentifier(s)) {
                    lex = createComponent(s, tokens.identifierTokens(s), ps);
                }else {
                    error = true;
                    System.out.println("Error en línea " + ps + ". Componente no reconocido: " + s);
                    if (error) {
                        this.dispose();
                        error = false;
                        return;
                    }
                }
            }

            if (lex != null) lexList.add(lex);
        });
    }

    private Component createComponent(String s, int tokenNumber, int ps) {
        if(validator.isIdentifier(s)){
            //System.out.println("Es un identificador: " + s);
            return new Component(s, tokenNumber, -2, ps);
        }else {
            return new Component(s, tokenNumber, -1, ps);
        }

    }



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JButton selectFileBttn;
    private javax.swing.JButton submitBttn;
    private javax.swing.JLabel titleLbl;
    // End of variables declaration//GEN-END:variables

    private void printOutputs(String s, int token, int pos, int ps) {
        System.out.println(s+","+token+","+pos+","+ps);
    }
}
