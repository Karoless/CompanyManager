package pl.mycompany.utils.conventers;

import pl.mycompany.database.models.Item;
import pl.mycompany.modelfx.ItemFx;

public class ItemConverter {

    public static Item convertItemFxToItem(ItemFx itemFx) {
        Item item = new Item();
        item.setId(itemFx.getId());
        item.setNameOfItem(itemFx.getName());
        item.setPrice(itemFx.getPrice());
        item.setNumberOfItems(itemFx.getValue());
        item.setId(itemFx.getId());

        return item;
    }

    public static ItemFx convertToFx(Item item) {
        ItemFx itemFx = new ItemFx();
        itemFx.setId(item.getId());
        itemFx.setName(item.getNamOfItem());
        itemFx.setPrice(item.getPrice());
        itemFx.setValue(item.getNumberOfItems());
        itemFx.setCategoryFxObjectProperty(CategoryConverter.convertToFX(item.getCategory()));
        return itemFx;
    }
}