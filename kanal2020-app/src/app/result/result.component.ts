import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { KanalRoute, Kanal } from '../models/Kanal.model';

@Component({
  selector: 'app-result',
  templateUrl: './result.component.html',
  styleUrls: ['./result.component.scss']
})
export class ResultComponent implements OnInit {

  public kanal: Kanal;
  public displayedColumns: string[] = ['column1', 'column2', 'column3', 'column4'];
  private routeParams: KanalRoute;


  constructor(private route: ActivatedRoute, private router: Router) { }

  public ngOnInit(): void {
    this.route.paramMap.subscribe((paramMap: any) => {
      this.routeParams = paramMap.params;
      this.kanal = Kanal.getNewKanal(this.routeParams);
    });
  }

  public onEditResult(): void {
    this.router.navigate(['/'], {
      queryParams: this.routeParams
    });
  }
}
