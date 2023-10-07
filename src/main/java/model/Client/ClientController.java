package model.Client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.DictionaryException;

public class ClientController{

    private Client client;

    private Thread thread;

    @FXML
    private TextField TextField_word;

    @FXML
    private TextField TextField_meanings;

    @FXML
    private TextArea TextArea_result;

    @FXML
    private Button Button_Add;

    @FXML
    private Button Button_Remove;

    @FXML
    private Button Button_Query;

    @FXML
    public void initialize() {
        Button_Add.setOnAction(event -> {
            // Wait for the thread to finish
            while(thread != null && thread.isAlive()) {
                System.out.println("Waiting...");
            }
            this.thread = new Thread(() -> {
                onAddButtonClick();
            });
            this.thread.start();
        });

        Button_Remove.setOnAction(event -> {
            // Wait for the thread to finish
            while(thread != null && thread.isAlive()) {
                System.out.println("Waiting...");
            }
            this.thread = new Thread(() -> {
                onRemoveButtonClick();
            });
            thread.start();
        });

        Button_Query.setOnAction(event -> {
            // Wait for the thread to finish
            while(thread != null && thread.isAlive()) {
                System.out.println("Waiting...");
            }
            this.thread = new Thread(() -> {
                onQueryButtonClick();
            });
            thread.start();
        });
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @FXML
    protected void onQueryButtonClick() {
        try {
            String word = TextField_word.getText();
            if (word.equals("")) {
                throw new DictionaryException("Please input a word!");
            } else {
                String command = "query@" + word; // construct request for query a word
                while(client.requestsQueue.size() == 0) {
                    client.requestsQueue.put(command);
                    break;
                }

                String respond = client.sendRequest(command);
                if(respond == null) {
                    throw new DictionaryException("Disconnected");
                } else {
                    TextArea_result.setText(respond);
                }
            }
        } catch(DictionaryException e) {
            System.out.println(e.getMessage());
            TextArea_result.setText(e.getMessage());
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        TextField_word.setText("");
        TextField_meanings.setText("");
    }

    // add a non-existed word with meanings
    @FXML
    protected void onAddButtonClick() {
        try {
            String word = TextField_word.getText();
            String meanings = TextField_meanings.getText();
            if (word.equals("")) {
                throw new DictionaryException("Please input a word!");
            } else if(meanings.equals("")) {
                throw new DictionaryException("Please input meaning(s)!");
            } else {
                String command = "add@" + word + "@" + meanings; // construct request for add a new word
                while(client.requestsQueue.size() == 0) {
                    client.requestsQueue.put(command);
                    break;
                }
                String respond = client.sendRequest(command);
                if(respond == null) {
                    throw new DictionaryException("Disconnected");
                } else {
                    TextArea_result.setText(respond);
                }
            }
        } catch(DictionaryException e) {
            System.out.println(e.getMessage());
            TextArea_result.setText(e.getMessage());
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            TextArea_result.setText(e.getMessage());
        }
        TextField_word.setText("");
        TextField_meanings.setText("");
    }
    @FXML
    protected void onRemoveButtonClick() {
//        d.deleteWord("app");
        try {
            String word = TextField_word.getText();
            if (word.equals("")) {
                throw new DictionaryException("Please input a word!");
            } else {
                String command = "delete@" + word; // construct request for delete a word
                while(client.requestsQueue.size() == 0) {
                    client.requestsQueue.put(command);
                    break;
                }
                String respond = client.sendRequest(command);
                if(respond == null) {
                    throw new DictionaryException("Disconnected");
                } else {
                    TextArea_result.setText(respond);
                }
            }
        } catch(DictionaryException e) {
            System.out.println(e.getMessage());
            TextArea_result.setText(e.getMessage());
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        TextField_word.setText("");
        TextField_meanings.setText("");
    }
}