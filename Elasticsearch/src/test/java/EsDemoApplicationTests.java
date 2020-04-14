import com.start.ElasticsearchApplication;
import com.start.entity.root.Item;
import com.start.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ElasticsearchApplication.class)
public class EsDemoApplicationTests {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private ItemRepository itemRepository;

    /**
     * 创建索引，会根据Item类的@Document注解信息来创建
     */
    @Test
    public void createIndex() {
        elasticsearchTemplate.createIndex(Item.class);
    }

    /**
     * 删除索引
     */
    @Test
    public void deleteIndex() {
        elasticsearchTemplate.deleteIndex(Item.class);
    }

    /**
     * 新增方法
     */
    @Test
    public void insert() {
        Item item = new Item(1L, "小米手机7", " 手机",
                "小米", 3499.00, "http://xxxxxxx.jpg");
        itemRepository.save(item);
    }

    /**
     * 批量新增方法
     */
    @Test
    public void insertList() {
        List<Item> list = new ArrayList<>();
        list.add(new Item(2L, "坚果手机R1", " 手机", "锤子", 3699.00, "http://xxxxxxx.jpg"));
        list.add(new Item(3L, "华为META10", " 手机", "华为", 4499.00, "http://xxxxxxx.jpg"));
        itemRepository.saveAll(list);
    }

    /**
     * 查询方法,对价格升序
     */
    @Test
    public void testQueryAll(){
        Iterable<Item> list = this.itemRepository.findAll(Sort.by("price").ascending());
        for (Item item:list){
            System.out.println(item);
        }
    }


}
