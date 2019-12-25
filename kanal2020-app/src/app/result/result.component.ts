import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { KanalRoute, Kanal } from '../models/Kanal.model';

@Component({
  selector: 'app-result',
  templateUrl: './result.component.html',
  styleUrls: ['./result.component.scss']
})
export class ResultComponent implements OnInit {

  public kanal: Kanal;
  public displayedColumns: string[] = ['column1', 'column2', 'column3', 'column4'];


  constructor(private route: ActivatedRoute) { }

  public ngOnInit(): void {
    this.route.paramMap.subscribe((paramMap: any) => {
      const routeParams: KanalRoute = paramMap.params;
      this.kanal = Kanal.getNewKanal(routeParams);
    });
  }
}
