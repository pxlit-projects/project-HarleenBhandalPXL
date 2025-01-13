import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Error404Component } from './error.404.component';
import { ActivatedRoute } from '@angular/router';

describe('Error404Component', () => {
  let component: Error404Component;
  let fixture: ComponentFixture<Error404Component>;
  let activatedRouteMock: any;

  beforeEach(() => {
    activatedRouteMock = { snapshot: { params: {} } };

    TestBed.configureTestingModule({
      imports: [Error404Component],
      providers: [
        { provide: ActivatedRoute, useValue: activatedRouteMock }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(Error404Component);
    component = fixture.componentInstance;
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });
});
