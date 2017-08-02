package pl.mycompany.utils.conventers;

import pl.mycompany.database.models.SaleList;
import pl.mycompany.modelfx.SaleListFx;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class SaleListConverter {
    public static SaleListFx convertToFx(SaleList saleList) {
        SaleListFx saleListFx = new SaleListFx();
        saleListFx.setId(saleList.getId());
        saleListFx.setDateOfSale(convertToLocalDate(saleList.getDateOfSale()));
        saleListFx.setCustomer(saleList.getCustomer());
        saleListFx.setItem_list(saleList.getItem_list());
        saleListFx.setValueOfItems(saleList.getValueOfItems());
        saleListFx.setTotalCostOfItem(saleList.getTotalCostOfItem());
        saleListFx.setTotalPrice(saleList.getTotalPrice());
        return saleListFx;
    }

    public static SaleList convertSaleListFxToCustomer(SaleListFx saleListFx) {
        SaleList saleList = new SaleList();
        saleList.setId(saleListFx.getId());
        saleList.setDateOfSale(convertToDate(saleListFx.getDateOfSale()));
        saleList.setCustomer(saleListFx.getCustomer());
        saleList.setItem_list(saleListFx.getItem_list());
        saleList.setValueOfItems(saleListFx.getValueOfItems());
        saleList.setTotalCostOfItem(saleListFx.getTotalCostOfItem());
        saleList.setTotalPrice(saleListFx.getTotalPrice());

        return saleList;
    }

    public static Date convertToDate(LocalDate localDate){
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate convertToLocalDate(Date date){
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}