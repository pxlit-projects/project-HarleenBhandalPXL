import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FilterComponent } from './filter.component';
import { provideNativeDateAdapter } from '@angular/material/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { By } from '@angular/platform-browser';

describe('FilterComponent', () => {
  let component: FilterComponent;
  let fixture: ComponentFixture<FilterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        FilterComponent,
        BrowserAnimationsModule,
        FormsModule,
        MatButtonModule,
        MatFormFieldModule,
        MatInputModule,
        MatDatepickerModule
      ],
      providers: [provideNativeDateAdapter()]
    }).compileComponents();

    fixture = TestBed.createComponent(FilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should emit filterChanged event on valid form submission', () => {
    spyOn(component.filterChanged, 'emit');

    component.filter.author = 'Test Author';
    component.filter.content = 'Test Content';
    component.filter.date = new Date('2025-01-01')

    const form = fixture.debugElement.query(By.css('form')).nativeElement;
    form.dispatchEvent(new Event('submit'));

    expect(component.filterChanged.emit).toHaveBeenCalledWith({
      author: 'test author',
      content: 'test content',
      date: new Date('2025-01-01')
    });
  });
});
