package sample;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Created by urankajtazaj on 02/11/16.
 */
public class BooleanCell extends TableCell<punetTbl, Boolean> {
    private static final String CON_STR = "jdbc:h2:file:~/db/auto;AUTO_SERVER=TRUE;MVCC=TRUE;LOCK_TIMEOUT=10000";
    private CheckBox checkBox;
    public BooleanCell() {
        checkBox = new CheckBox();
        checkBox.setDisable(false);

        checkBox.selectedProperty().addListener((ov, old, newValue) -> {
            if(isEditing())
            {
                commitEdit(newValue == null ? false : newValue);
            }
        });
        this.setGraphic(checkBox);
//        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        this.setEditable(true);
    }

    @Override
    public void startEdit() {
        super.startEdit();
        if (isEmpty()) {
            return;
        }
        checkBox.setDisable(false);
        checkBox.requestFocus();
    }
    @Override
    public void cancelEdit() {
        super.cancelEdit();
        checkBox.setDisable(true);
    }

    public void commitEdit(Boolean value) {
        super.commitEdit(value);
        checkBox.setDisable(true);
    }

    @Override
    public void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);
        if (!isEmpty()) {
            checkBox.setSelected(item);
            setGraphic(checkBox);
        }else setGraphic(null);
    }
}
