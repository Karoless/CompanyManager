package pl.mycompany.utils.conventers;

import pl.mycompany.database.models.Sale;
import pl.mycompany.modelfx.SaleFx;

public class SaleConverter {

    public static Sale convertSaleFxToSale(SaleFx saleFX) {
        Sale sale = new Sale();
        sale.setId(saleFX.getId());
        sale.setBoughtItems(saleFX.getBoughtItems());
        sale.setNumberOfBoughtItems(saleFX.getNumberOfBoughtItems());
        sale.setTotalCostOfItem(saleFX.getTotalPrice());
        sale.setTotalPrice(saleFX.getTotalPriceOfShopping());
        return sale;
    }

    public static SaleFx convertToFx(Sale sale) {
        SaleFx saleFx = new SaleFx();
        saleFx.setId(sale.getId());
        saleFx.setBoughtItems(sale.getBoughtItems());
        saleFx.setNumberOfBoughtItems(sale.getNumberOfBoughtItems());
        saleFx.setTotalPrice(sale.getTotalCostOfItem());
        saleFx.setTotalPriceOfShopping(sale.getTotalPrice());
        return saleFx;
    }
}