import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {StockService} from "../../service/stock.service";
import * as moment from "moment";

@Component({
    selector: 'app-stock-list',
    templateUrl: './stock-list.component.html',
    styleUrls: ['./stock-list.component.css']
})
export class StockListComponent implements OnInit {

    stocks: Array<any>;

    constructor(private stockService: StockService, private route: ActivatedRoute) {
    }

    ngOnInit() {
        this.stockService.getStockList().subscribe(res => {
            this.stocks = res['data'];
            for (let stock of this.stocks) {
                stock['lastUpdatedMoment'] = moment(stock['lastUpdated']).format('MMMM Do YYYY, h:mm:ss a');
            }
        });
    }
}
