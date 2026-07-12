module br.edu.ifba.saj.ads.poo {
    requires javafx.controls;
    requires javafx.fxml;

    opens br.edu.ifba.saj.ads.poo to javafx.fxml;
    opens br.edu.ifba.saj.ads.poo.presentation to javafx.fxml;
    opens br.edu.ifba.saj.ads.poo.model to javafx.base;

    exports br.edu.ifba.saj.ads.poo;
}
