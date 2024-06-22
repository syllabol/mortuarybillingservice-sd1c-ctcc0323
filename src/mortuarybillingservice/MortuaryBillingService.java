package mortuarybillingservice;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.border.TitledBorder;

public class MortuaryBillingService extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField nameField, addressField, contactField;
    private JCheckBox embalmingBox, burialBox, cremationBox;
    private JButton createButton, readButton, updateButton, deleteButton;
    private JTextArea billArea;
    private List<Bill> bills;
    private int currentBillIndex = -1;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton createAccountButton;
       
    

    public MortuaryBillingService 
        () throws IOException {
    	setIconImage(Toolkit.getDefaultToolkit().getImage(MortuaryBillingService.class.getResource("/mortuarybillingservice/coff.png")));
    	if (!showAccountCreationDialog()) {
            System.exit(0);
        }
    	
        bills = new ArrayList<>();

        setTitle("LouieBingan");
        setSize(500, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        
        
        
        // Create input panel
        GridLayout gl_inputPanel = new GridLayout(4, 4);
        JPanel inputPanel = new JPanel(gl_inputPanel);
        inputPanel.setBorder(new TitledBorder(UIManager.getBorder("CheckBoxMenuItem.border"), "Client Information", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(51, 51, 51)));

        JLabel label_1 = new JLabel("Name:");
        label_1.setFont(new Font("Palatino Linotype", Font.BOLD, 20));
        inputPanel.add(label_1);
        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(100, 50));
        inputPanel.add(nameField);
        

        JLabel label_2 = new JLabel("Address:");
        label_2.setFont(new Font("Palatino Linotype", Font.BOLD, 20));
        inputPanel.add(label_2);
        addressField = new JTextField();
        addressField.setPreferredSize(new Dimension(100, 50));
        inputPanel.add(addressField);
        

        JLabel label = new JLabel("Contact:");
        label.setFont(new Font("Palatino Linotype", Font.BOLD, 20));
        inputPanel.add(label);
        contactField = new JTextField();
        contactField.setPreferredSize(new Dimension(100, 50));
        inputPanel.add(contactField);

        JLabel label_3 = new JLabel("Services:");
        label_3.setFont(new Font("Palatino Linotype", Font.BOLD, 20));
        inputPanel.add(label_3);

        // Create services panel
        GridLayout gl_servicesPanel = new GridLayout(3, 1);
        JPanel servicesPanel = new JPanel(gl_servicesPanel);
        embalmingBox = new JCheckBox("  Embalming (Php6000)\r\n");
        embalmingBox.setVerticalAlignment(SwingConstants.TOP);
        embalmingBox.setHorizontalAlignment(SwingConstants.CENTER);
        embalmingBox.setFont(new Font("Palatino Linotype", Font.BOLD, 20));
        embalmingBox.setForeground(new Color(0, 0, 0));
        burialBox = new JCheckBox("          Burial (Php12000)\r\n");
        burialBox.setVerticalAlignment(SwingConstants.TOP);
        burialBox.setHorizontalAlignment(SwingConstants.CENTER);
        burialBox.setForeground(new Color(0, 0, 0));
        burialBox.setFont(new Font("Palatino Linotype", Font.BOLD, 20));
        cremationBox = new JCheckBox("    Cremation (Php7000)");
        cremationBox.setVerticalAlignment(SwingConstants.TOP);
        cremationBox.setHorizontalAlignment(SwingConstants.CENTER);
        cremationBox.setForeground(new Color(0, 0, 0));
        cremationBox.setFont(new Font("Palatino Linotype", Font.BOLD, 20));

        servicesPanel.add(embalmingBox);
        servicesPanel.add(burialBox);
        servicesPanel.add(cremationBox);

        inputPanel.add(servicesPanel);

        getContentPane().add(inputPanel, BorderLayout.NORTH);

        // Create button panel
        GridLayout gl_buttonPanel = new GridLayout(1, 1);
        gl_buttonPanel.setHgap(3);
        JPanel buttonPanel = new JPanel(gl_buttonPanel);
        createButton = new JButton("Create Bill");
        readButton = new JButton("Read Bill");
        updateButton = new JButton("Update Bill");
        deleteButton = new JButton("Delete Bill");

        buttonPanel.add(createButton);
        buttonPanel.add(readButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        getContentPane().add(buttonPanel, BorderLayout.CENTER);
        

        // Create bill area
        billArea = new JTextArea(12, 40);
        billArea.setBorder(BorderFactory.createTitledBorder("Bill"));
        getContentPane().add(new JScrollPane(billArea), BorderLayout.SOUTH);

        // Add action listeners
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createBill();
            }
        });

        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readBill();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBill();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteBill();
            }
        });
    }

    private boolean showAccountCreationDialog() {
        JDialog accountDialog = new JDialog(this, "Login", true);
        accountDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        accountDialog.setBackground(new Color(204, 204, 255));
        accountDialog.setAlwaysOnTop(true);
        accountDialog.setSize(450, 450);
        accountDialog.getContentPane().setLayout(new BorderLayout());
        accountDialog.setLocationRelativeTo(null); // Center the dialog

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        ImageIcon image = new ImageIcon("/mortuarybillingservice/coff.png");
        accountDialog.setIconImage(Toolkit.getDefaultToolkit().getImage(MortuaryBillingService.class.getResource("/mortuarybillingservice/coff.png")));
        
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon(MortuaryBillingService.class.getResource("/mortuarybillingservice/LOUBI.png")));
        contentPanel.add(lblNewLabel, BorderLayout.NORTH);
        
        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Monotype Corsiva", Font.BOLD, 40));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(titleLabel, BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 0));
        formPanel.add(new JLabel("Username:"));
        usernameField = new JTextField(20);
        formPanel.add(usernameField);

        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField(20);
        formPanel.add(passwordField);

        createAccountButton = new JButton("Login");
        formPanel.add(new JLabel()); // Placeholder for alignment
        formPanel.add(createAccountButton);

        contentPanel.add(formPanel, BorderLayout.SOUTH);
        accountDialog.getContentPane().add(contentPanel, BorderLayout.CENTER);
        
        final boolean[] accountCreated = {false};

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(accountDialog, "Please sign up first.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Perform account creation logic here
                    JOptionPane.showMessageDialog(accountDialog, "Login success!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    accountCreated[0] = true;
                    accountDialog.dispose(); // Close the dialog
                }
            }
        });

        accountDialog.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                if (!accountCreated[0]) {
                    System.exit(0); // Exit the application when the account dialog is closed without creating an account
                }
            }
        });

        accountDialog.setVisible(true); // Show the dialog
        return accountCreated[0];
    }


    private void createBill() {
        String name = nameField.getText();
        String address = addressField.getText();
        String contact = contactField.getText();

        int total = 0;
        StringBuilder services = new StringBuilder();
        if (embalmingBox.isSelected()) {
            total += 500;
            services.append("- Embalming: $500\n");
        }
        if (burialBox.isSelected()) {
            total += 2000;
            services.append("- Burial: $2000\n");
        }
        if (cremationBox.isSelected()) {
            total += 1500;
            services.append("- Cremation: $1500\n");
        }

        Bill bill = new Bill(name, address, contact, services.toString(), total);
        bills.add(bill);

        billArea.setText("Bill Created:\n" + bill.toString());
        clearFields();
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("member.txt", true))) {
			writer.write(bill.toString());
			writer.newLine();
			writer.write("------------------");
			writer.newLine();
			System.out.println("Bill written successfully. \n");
			JOptionPane.showMessageDialog(this, "Bill created!", "Success", JOptionPane.INFORMATION_MESSAGE);
            return;
		} catch (IOException e) {
			System.out.println("Failed to write to the file. \n");
			e.printStackTrace();}
    }

    private void readBill() {
    	String name = JOptionPane.showInputDialog(this, "Enter client name to search:");
        for (int i = 0; i < bills.size(); i++) {
            if (bills.get(i).getName().equalsIgnoreCase(name)) {
            	
            	try (BufferedReader reader = new BufferedReader(new FileReader("member.txt"))) {
        			System.out.println("Read from file:");
        			while ((name = reader.readLine()) != null) {
        				System.out.println(name);
        			}	
        		} catch (IOException e) {
        			System.out.println("Failed to read from the file.");
        			e.printStackTrace();
        		}
            	
                currentBillIndex = i;
                Bill bill = bills.get(i);
                billArea.setText("Bill Found:\n" + bill.toString());
                nameField.setText(bill.getName());
                addressField.setText(bill.getAddress());
                contactField.setText(bill.getContact());
                embalmingBox.setSelected(bill.getServices().contains("Embalming"));
                burialBox.setSelected(bill.getServices().contains("Burial"));
                cremationBox.setSelected(bill.getServices().contains("Cremation"));
                JOptionPane.showMessageDialog(this, "Found bill!", "Success", JOptionPane.INFORMATION_MESSAGE);
                return;
            } else {
                billArea.setText("Bill not found.");
            }
        }
        
		
    }

    private void updateBill() {
        if (currentBillIndex == -1) {
            billArea.setText("No bill selected to update.");
            return;
        }

        Bill bill = bills.get(currentBillIndex);
        bill.setName(nameField.getText());
        bill.setAddress(addressField.getText());
        bill.setContact(contactField.getText());

        int total = 0;
        StringBuilder services = new StringBuilder();
        if (embalmingBox.isSelected()) {
            total += 500;
            services.append("- Embalming: $500\n");
        }
        if (burialBox.isSelected()) {
            total += 2000;
            services.append("- Burial: $2000\n");
        }
        if (cremationBox.isSelected()) {
            total += 1500;
            services.append("- Cremation: $1500\n");
        }
        bill.setServices(services.toString());
        bill.setTotal(total);

        billArea.setText("Bill Updated:\n" + bill.toString());
        clearFields();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("member.txt", true))) {
        	writer.write("------------------");
			writer.write("UPDATED\n" + bill.toString());
			writer.newLine();
			System.out.println("File updated.");
			JOptionPane.showMessageDialog(this, "Bill updated!", "Success", JOptionPane.INFORMATION_MESSAGE);
            return;
		} catch (IOException e) {
			System.out.println("Failed to update the file.");
			e.printStackTrace();
		}
    }

    private void deleteBill() {
    	File file = new File("member.txt");
        
        if (currentBillIndex == -1) {
            billArea.setText("No bill selected to delete.");
            System.out.println("Failed to delete file.");
            return;
        } else {
        	JFrame frame = new JFrame("Delete?");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(300, 100);
            frame.getContentPane().setLayout(new BorderLayout());

            // Create a label
            JPanel labelPanel = new JPanel();
            JLabel label = new JLabel("Do you want to delete the bill?");
            labelPanel.add(label);
            frame.getContentPane().add(labelPanel, BorderLayout.NORTH);

            JPanel buttonPanel = new JPanel();
            JButton yesButton = new JButton("Yes");
            JButton noButton = new JButton("No");
            buttonPanel.add(yesButton);
            buttonPanel.add(noButton);
            frame.getContentPane().add(buttonPanel, BorderLayout.CENTER);
            
            yesButton.addActionListener(new ActionListener() {
            	
                public void actionPerformed(ActionEvent e) {
                	if (file.delete()) {
                        bills.remove(currentBillIndex);
                        billArea.setText("Bill Deleted.");
                        frame.dispose();
                        clearFields();
                        currentBillIndex = -1;
                        System.out.println("File deleted.");
                        JOptionPane.showMessageDialog(frame, "Bill deleted!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                	
                }
            });

            // Add action listener for the No button
            noButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	frame.dispose();
                    return;
                }
            });
            
            // Center the frame on the screen
            frame.setLocationRelativeTo(null);

            // Set frame visibility
            frame.setVisible(true);
        }
    }

    private void clearFields() {
        nameField.setText("");
        addressField.setText("");
        contactField.setText("");
        embalmingBox.setSelected(false);
        burialBox.setSelected(false);
        cremationBox.setSelected(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
        	public void run() {
        		try {
					new MortuaryBillingService().setVisible(true);
				} catch (IOException e) {
					e.printStackTrace();
				}
                }
        });
    }

    class Bill {
        private String name, address, contact, services;
        private int total;

        public Bill(String name, String address, String contact, String services, int total) {
            this.name = name;
            this.address = address;
            this.contact = contact;
            this.services = services;
            this.total = total;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getServices() {
            return services;
        }

        public void setServices(String services) {
            this.services = services;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        @Override
        public String toString() {
            return "Name: " + name + "\n" +
                   "Address: " + address + "\n" +
                   "Contact: " + contact + "\n" +
                   "Services:\n" + services +
                   "Total: $" + total;
        }
    }
}
