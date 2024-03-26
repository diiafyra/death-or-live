/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import static demo.Handler.intErrorTable;
import demo.cndb;
import java.util.EventObject;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

/**
 *
 * @author DELL
 */
public class orderDetailTable extends JTable {
    
    public orderDetailTable(JLabel errMess){
        setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "Mã sản phẩm","Sản phẩm", "Số lượng", "Đơn giá","Giá"
            }
        ));
        
        TableColumn productColumn = getColumnModel().getColumn(1);
        JComboBox<String> comboB = new JComboBox<>();
        cndb db = cndb.getInstance();
        db.open();
        List<Product> allPro = db.allProducts();
        db.close();        
        for(Product pro : allPro){
            comboB.addItem(pro.getName_p());
        }
        productColumn.setCellEditor(new DefaultCellEditor(comboB));
        
        getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int column = e.getColumn();
                    if (column == 0 || column == 2) { // Check if changed column is quantity or price
                        if(column == 2){
                            errMess.setText(intErrorTable(orderDetailTable.this, e));
                        }
                        int row = e.getFirstRow();
                        if( getValueAt(row, 0) !=null && getValueAt(row, 2)!=null){
                            int quantity = Integer.parseInt(getValueAt(row, 2).toString());
                            String id_p = (String) getValueAt(row, 0);
                            cndb db = cndb.getInstance();
                            db.open();
                            int stock = db.getStock(id_p);
                            db.close();
                            int preQual =0;
                            
                            for(int i =0; i<getRowCount(); i++){
                                String id = (String) getValueAt(i, 0);
                                String qual = (String) getValueAt(i, 2);
                                if(id!= null && qual != null && id.equals(id_p)){
                                    preQual += Integer.valueOf(qual);
                                }
                            }
                            System.out.println(preQual);
                            if(quantity>(stock - preQual+quantity)){
                                JOptionPane.showMessageDialog(orderDetailTable.this, "Vượt Quá Số Lượng Tồn Kho. Tồn Kho: " + stock);
                                setValueAt(null, row, 2);
                            }
                            float price = Float.valueOf(getValueAt(row, 3).toString());
                            float totalPrice = quantity * price;
                            setValueAt(totalPrice, row, 4); // Update total price column
                        }
                    }
                }
            }
        });
        
        TableCellEditor nonEditableEditor = new DefaultCellEditor(new JTextField()) {
            @Override
            public boolean isCellEditable(EventObject e) {
                return false;
            }
        };

        // Thiết lập trình biên tập tùy chỉnh cho cột cụ thể
        getColumnModel().getColumn(3).setCellEditor(nonEditableEditor);
    }
}
