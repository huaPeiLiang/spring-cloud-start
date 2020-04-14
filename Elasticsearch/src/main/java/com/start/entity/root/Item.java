package com.start.entity.root;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


/**
 * indexName：对应索引库名称
 * type：对应在索引库中的类型
 * shards：分片数量，默认5
 * replicas：副本数量，默认1
 * */
@Data
@Document(indexName = "dataBase",type = "item", shards = 1, replicas = 0)
public class Item {

    @Id
    private Long id;

    /**
     * type：字段类型，是枚举：FieldType，可以是text、long、short、date、integer、object等
     *      text：存储数据时候，会自动分词，并生成索引
     *      keyword：存储数据时候，不会分词建立索引
     *      Numerical：数值类型，分两类
     *          基本数据类型：long、interger、short、byte、double、float、half_float
     *          浮点数的高精度类型：scaled_float
     *          需要指定一个精度因子，比如10或100。elasticsearch会把真实值乘以这个因子后存储，取出时再还原。
     *      Date：日期类型
     *          elasticsearch可以对日期格式化为字符串存储，但是建议我们存储为毫秒值，存储为long，节省空间。
     * index：是否索引，布尔类型，默认是true
     * store：是否存储，布尔类型，默认是false
     * analyzer：分词器名称，这里的ik_max_word即使用ik分词器
     * */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title; //标题

    @Field(type = FieldType.Keyword)
    private String category;// 分类

    @Field(type = FieldType.Keyword)
    private String brand; // 品牌

    @Field(type = FieldType.Double)
    private Double price; // 价格

    @Field(index = false, type = FieldType.Keyword)
    private String images; // 图片地址

    public Item() {}

    public Item(long id, String title, String category, String brand, double price, String images) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.brand = brand;
        this.price = price;
        this.images = images;
    }


}
