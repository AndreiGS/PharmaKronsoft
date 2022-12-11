package com.kronsoft.pharma.article;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="article")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Article {

    @Id
    @SequenceGenerator(
            name = "article_sequence",
            sequenceName = "article_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "article_sequence"
    )
    Integer id;
    String PZN;
    String Produktname;
    String HerstellerId;
    String KurzeBeschreibung;

    @Column(length=10000)
    String Beschreibung;
    String QuantityUomId;
    String ProduktFormId;
    String CustomQuantity;
    String ProduktnameNoSpaces;
    String Hersteller;

    @Column(length = 500)
    String Keywords;
    String KategorieName;
    String KategorieId;
    String ProduktForm;
    String Price;
    String Tierarzneimittel;
    String Wirkstoffe;
    String Kuehlkette;
    String Rezeptpflicht;
    String Availability;
    String IsShopArticle;
    String SalesCount;
    String ReImported;
    String IsLmivArticle;
    String EanUpc;

    public Article(String[] csvRow) { // TODO beautify
        this(Integer.valueOf(csvRow[0]), csvRow[1], csvRow[2], csvRow[3], csvRow[4], csvRow[5], csvRow[6], csvRow[7], csvRow[8], csvRow[9],
                csvRow[10], csvRow[11], csvRow[12], csvRow[13], csvRow[14], csvRow[15], csvRow[16], csvRow[17], csvRow[18], csvRow[19],
                csvRow[20], csvRow[21], csvRow[22], csvRow[23], csvRow[24], csvRow[25]);

    }
}
