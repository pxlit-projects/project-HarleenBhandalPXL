import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http'
import { PendingComponent } from './pending.component';

describe('PendingComponent', () => {
  let component: PendingComponent;
  let fixture: ComponentFixture<PendingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PendingComponent],
      providers: [provideHttpClient()]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PendingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
