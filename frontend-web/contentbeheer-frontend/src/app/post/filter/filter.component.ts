import {ChangeDetectionStrategy, Component, EventEmitter, Output} from '@angular/core';
import {Filter} from "../../models/filter.model";
import {FormsModule} from "@angular/forms";
import {MatButton} from "@angular/material/button";
import {MatFormField, MatInputModule} from "@angular/material/input";
import {MatDatepicker, MatDatepickerInput, MatDatepickerToggle} from "@angular/material/datepicker";

@Component({
  selector: 'app-filter',
  standalone: true,
  imports: [
    FormsModule,
    MatButton,
    MatInputModule,
    MatFormField,
    MatDatepickerInput,
    MatDatepickerToggle,
    MatDatepicker
  ],
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './filter.component.html',
  styleUrl: './filter.component.css'
})
export class FilterComponent {
  filter: Filter = { author: '', content: '', date: undefined };

  @Output() filterChanged = new EventEmitter<Filter>();

  constructor() {}

  onSubmit(form: any): void {
    this.filter.author = this.filter.author.trim().toLowerCase();
    this.filter.content = this.filter.content.trim().toLowerCase();
    this.filter.date = this.filter.date ? new Date(this.filter.date) : undefined;

    this.filterChanged.emit(this.filter);
  }
}
