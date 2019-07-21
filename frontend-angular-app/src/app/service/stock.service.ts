import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable()
export class StockService {

    private stockListEndpoint: string = environment.apiBaseEndpoint + '/api/stocks';

    constructor(private http: HttpClient) {
    }

    public getStockList(): Observable<any> {
        return this.http.get<any>(this.stockListEndpoint);
    }
}
