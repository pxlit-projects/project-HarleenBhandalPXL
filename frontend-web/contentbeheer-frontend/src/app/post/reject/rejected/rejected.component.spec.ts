import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RejectedComponent } from './rejected.component';
import { provideHttpClient } from '@angular/common/http';

describe('RejectedComponent', () => {
  let component: RejectedComponent;
  let fixture: ComponentFixture<RejectedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RejectedComponent,
      ],
      providers: [
        provideHttpClient(),
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(RejectedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
