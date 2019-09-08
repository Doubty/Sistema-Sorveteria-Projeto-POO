package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import bean.Vendedor;
import excecao.ModelValidar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.ModelValidarVendedor;

public class FXMLAnchorPaneCadastrosVendedoresInserirController implements Initializable {

    @FXML
    private Label labelVendedorNome;
    @FXML
    private Label labelVendedorSobrenome;
    @FXML
    private Label labelVendedorEmail;
    @FXML
    private Label labelVendedorTelefone;
    @FXML
    private Label labelVendedorEndereco;
    @FXML
    private Label labelVendedorDataNascimentoe;
    @FXML
    private Label labelVendedorSalario;
    @FXML
    private Label labelVendedorCpf;
    @FXML
    private Label labelVendedorTurno;
    @FXML
    private Label labelVendedorDataInicio;
    @FXML
    private Label labelVendedorCargaHoraria;
    @FXML
    private Label labelVendedorIdUsuario;

    @FXML
    private TextField textFieldVendedorNome;
    @FXML
    private TextField textFieldVendedorSobrenome;
    @FXML
    private TextField textFieldVendedorEmail;
    @FXML
    private TextField textFieldVendedorTelefone;
    @FXML
    private TextField textFieldVendedorEndereco;
    @FXML
    private TextField textFieldVendedorSalario;
    @FXML
    private TextField textFieldVendedorCpf;
    @FXML
    private TextField textFieldVendedorCargaHoraria;
    @FXML
    private TextField textFieldVendedorIdUsuario;
    @FXML
    private DatePicker datePickerDataNascimento;
    @FXML
    private DatePicker datePickerDataInicio;

    @FXML
    private Button buttonVendedorConfirmar;
    @FXML
    private Button buttonVendedorCancelar;

    @FXML
    private ComboBox<String> comboboxTurno;

    private Stage dialogStage; // Atributo para abrir e fechar a tela cadastro vendedor
    private boolean buttonConfirmarClicked = false; // Atributo para verificar se o botão foi clicado
    private Vendedor vendedor; // Classe usada como modelo para inserir os dados

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarComboBoxTurno();
    }

    // Gets e setters dos atributos
    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    public void setButtonConfirmarClicked(boolean buttonConfirmarClicked) {
        this.buttonConfirmarClicked = buttonConfirmarClicked;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
        // Setar nos campos para começar aparecendo os valores do vendedor
        this.textFieldVendedorNome.setText(vendedor.getNome());
        this.textFieldVendedorSobrenome.setText(vendedor.getSobrenome());
        this.textFieldVendedorEmail.setText(vendedor.getEmail());
        this.textFieldVendedorTelefone.setText(vendedor.getTelefone());
        this.textFieldVendedorEndereco.setText(vendedor.getEndereco());
        this.datePickerDataNascimento.setValue(vendedor.getData_nasc());
        this.textFieldVendedorSalario.setText("" + vendedor.getSalario());
        this.textFieldVendedorCpf.setText(vendedor.getCpf());
        this.comboboxTurno.setValue(vendedor.getTurno());
        this.datePickerDataInicio.setValue(vendedor.getData_inicio());
        this.textFieldVendedorCargaHoraria.setText("" + vendedor.getCarga_horaria());
        this.textFieldVendedorIdUsuario.setText("" + vendedor.getId_usuario());
    }

    @FXML
    public void handlButtonConfirmar() {

        try {
            vendedor.setNome(textFieldVendedorNome.getText());
            vendedor.setSobrenome(textFieldVendedorSobrenome.getText());
            vendedor.setEmail(textFieldVendedorEmail.getText());
            vendedor.setTelefone(textFieldVendedorTelefone.getText());
            vendedor.setData_nasc(datePickerDataNascimento.getValue());
            vendedor.setSalario(Float.parseFloat(textFieldVendedorSalario.getText()));
            vendedor.setEndereco((textFieldVendedorEndereco.getText()));
            vendedor.setCpf(textFieldVendedorCpf.getText());
            vendedor.setTurno(comboboxTurno.getSelectionModel().getSelectedItem());
            vendedor.setData_inicio(datePickerDataInicio.getValue());
            vendedor.setCarga_horaria(Integer.parseInt(textFieldVendedorCargaHoraria.getText()));
            vendedor.setId_usuario(Integer.parseInt(textFieldVendedorIdUsuario.getText()));

            ModelValidarVendedor.validarEntradaDeDados(vendedor);

            buttonConfirmarClicked = true;
            dialogStage.close();

        } catch (ModelValidar e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Vendedor:");
            alert.setHeaderText("Inserção de vendedor:");
            alert.setContentText(e.getMessage());
            alert.show();
        }

    }

    @FXML
    public void handlButtonCancelar() {
        dialogStage.close();
    }

    public void carregarComboBoxTurno() {

        List<String> turnos = new ArrayList<String>();
        ObservableList<String> observableListTurnos;

        String t1 = "Manhã";
        String t2 = "Tarde";
        String t3 = "Noite";

        turnos.add(t1);
        turnos.add(t2);
        turnos.add(t3);
        observableListTurnos = FXCollections.observableArrayList(turnos);
        comboboxTurno.setItems(observableListTurnos);

    }

}
