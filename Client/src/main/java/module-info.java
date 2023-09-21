module com.example.client {

    requires org.jetbrains.annotations;
    requires java.sql;
    requires org.pomo.toasterfx;
    requires de.jensd.fx.glyphs.fontawesome;
    requires org.json;
    requires java.desktop;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;




    opens com.example.client to javafx.fxml;

//--------------------------------------------------------------------------------------------------------------------------------


//    opens client.controllers to javafx.media, java.desktop , org.json , org.jetbrains.annotations, java.sql, org.pomo.toasterfx,
//            de.jensd.fx.glyphs.fontawesome , javafx.fxml , javafx.controls , javafx.graphics ;
//    opens client.models to javafx.media, java.desktop , org.json , org.jetbrains.annotations, java.sql, org.pomo.toasterfx,
//            de.jensd.fx.glyphs.fontawesome , javafx.fxml , javafx.controls ;
//    opens database to javafx.media, java.desktop , org.json , org.jetbrains.annotations, java.sql, org.pomo.toasterfx,
//            de.jensd.fx.glyphs.fontawesome , javafx.fxml , javafx.controls , javafx.graphics ;





    exports client.controllers ;
    exports  client.models;
    exports database ;

    //----------------------------------------------------------------------------------------------------------------------------------

    exports com.example.client;
    exports main ;
    exports pieces.graphics ;
    exports Animators ;
    exports board.graphics;
    exports board ;
    exports  highlighters.graphics;
    exports highlighters ;
    exports log;
    exports networking;
    exports pieces;
    exports stackRoots;
    exports utils ;
}