package sample;

import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;

public class TreeModels {

    public static final TreeItem<String> getCorporateTree() {
        CheckBoxTreeItem<String> management = new CheckBoxTreeItem<String>("Management");
        CheckBoxTreeItem<String> directors = new CheckBoxTreeItem<String>("Directors");
        CheckBoxTreeItem<String> finance = new CheckBoxTreeItem<String>("Finance");
        management.getChildren().addAll(directors, finance);
        management.setExpanded(true);

        CheckBoxTreeItem<String> sales = new CheckBoxTreeItem<String>("Sales");
        CheckBoxTreeItem<String> marketing = new CheckBoxTreeItem<String>("Marketing");
        CheckBoxTreeItem<String> assistants = new CheckBoxTreeItem<String>("Assistants");
        sales.getChildren().addAll(marketing, assistants);
        sales.setExpanded(true);

        CheckBoxTreeItem<String> engineering = new CheckBoxTreeItem<String>("Engineering");
        CheckBoxTreeItem<String> software = new CheckBoxTreeItem<String>("Software");
        CheckBoxTreeItem<String> hardware = new CheckBoxTreeItem<String>("Hardware");
        CheckBoxTreeItem<String> rAndD = new CheckBoxTreeItem<String>("R & D");
        engineering.getChildren().addAll(software, hardware, rAndD);
        engineering.setExpanded(true);

        CheckBoxTreeItem<String> legal = new CheckBoxTreeItem<String>("Legal");

        final CheckBoxTreeItem<String> root = new CheckBoxTreeItem<String>("EnergyCo");
        root.getChildren().addAll(management, sales, engineering, legal);
        root.setSelected(true);

        return root;
    }

    public static final TreeItem<String> getFamiliesTree() {
        // Brady bunch
        CheckBoxTreeItem<String> marciaBrady = new CheckBoxTreeItem<String>("Marcia");
        CheckBoxTreeItem<String> carolBrady = new CheckBoxTreeItem<String>("Carol");
        CheckBoxTreeItem<String> gregBrady = new CheckBoxTreeItem<String>("Greg");
        CheckBoxTreeItem<String> peterBrady = new CheckBoxTreeItem<String>("Peter");
        CheckBoxTreeItem<String> bobbyBrady = new CheckBoxTreeItem<String>("Bobby");
        CheckBoxTreeItem<String> mikeBrady = new CheckBoxTreeItem<String>("Mike");
        CheckBoxTreeItem<String> cindyBrady = new CheckBoxTreeItem<String>("Cindy");
        CheckBoxTreeItem<String> janBrady = new CheckBoxTreeItem<String>("Jan");

        CheckBoxTreeItem<String> bradyFamily = new CheckBoxTreeItem<String>("Brady Family");
        bradyFamily.getChildren().addAll(marciaBrady, carolBrady, gregBrady, peterBrady, bobbyBrady, mikeBrady, cindyBrady, janBrady);

        // Giles family
        CheckBoxTreeItem<String> jonathanGiles = new CheckBoxTreeItem<String>("Jonathan");
        CheckBoxTreeItem<String> juliaGiles = new CheckBoxTreeItem<String>("Julia");
        CheckBoxTreeItem<String> mattGiles = new CheckBoxTreeItem<String>("Matt");
        CheckBoxTreeItem<String> sueGiles = new CheckBoxTreeItem<String>("Sue");
        CheckBoxTreeItem<String> ianGiles = new CheckBoxTreeItem<String>("Ian");

        CheckBoxTreeItem<String> gilesFamily = new CheckBoxTreeItem<String>("Giles Family");
        gilesFamily.getChildren().addAll(jonathanGiles, juliaGiles, mattGiles, sueGiles, ianGiles);

        final CheckBoxTreeItem<String> root = new CheckBoxTreeItem<String>("Families");
        root.getChildren().addAll(gilesFamily, bradyFamily);

        return root;
    }
}
