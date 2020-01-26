import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { KanalRoute } from '../models/Kanal.model';

@Component({
  selector: 'app-input',
  templateUrl: './input.component.html',
  styleUrls: ['./input.component.scss']
})
export class InputComponent implements OnInit {

  public kanalForm: FormGroup;

  private a: number;
  private b: number;
  private d: number;

  public hintE: number;
  public hintF: number;

  private DEFAULT_VALIDATOR_ARRAY = [
    Validators.required,
    Validators.minLength(1),
    Validators.maxLength(4),
    Validators.min(-1000),
    Validators.max(1000)];

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private route: ActivatedRoute) { }

  public ngOnInit(): void {
    this.setUpForm();
    this.route.queryParamMap.subscribe((paramMap) => {
      paramMap.keys.forEach(key => {
        this.kanalForm.controls[key].setValue(paramMap.get(key));
      });
    });
  }

  public onSubmit(): void {
    this.router.navigate(['/result'], {
      queryParams: this.kanalForm.value
    });
  }

  private setUpForm(): void {
    this.kanalForm = this.formBuilder.group({
      a: ['', this.DEFAULT_VALIDATOR_ARRAY],
      b: ['', this.DEFAULT_VALIDATOR_ARRAY],
      d: ['', this.DEFAULT_VALIDATOR_ARRAY],
      l: ['', this.DEFAULT_VALIDATOR_ARRAY],
      dicke: ['', this.DEFAULT_VALIDATOR_ARRAY],
      e: ['', this.DEFAULT_VALIDATOR_ARRAY],
      f: ['', this.DEFAULT_VALIDATOR_ARRAY],
    });
    this.kanalForm.get('a').valueChanges.subscribe(
      (value) => {
        this.a = parseInt(value, 10);
        if (this.a && this.d) {
          this.calculateHintF();
        }
      });
    this.kanalForm.get('b').valueChanges.subscribe(
      (value) => {
        this.b = parseInt(value, 10);
        if (this.b && this.d) {
          this.calculateHintE();
        }
      });
    this.kanalForm.get('d').valueChanges.subscribe(
      (value) => {
        this.d = parseInt(value, 10);
        if (this.b) {
          this.calculateHintE();
        }
        if (this.a) {
          this.calculateHintF();
        }
      });
  }

  private calculateHintE(): void {
    this.hintE = (this.d - this.b) / 2;
  }

  private calculateHintF(): void {
    this.hintF = (this.d - this.a) / 2;
  }

}
