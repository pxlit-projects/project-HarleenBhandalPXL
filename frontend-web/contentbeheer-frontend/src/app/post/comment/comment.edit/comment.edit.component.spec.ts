import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommentEditComponent } from './comment.edit.component';

describe('CommentEditComponent', () => {
  let component: CommentEditComponent;
  let fixture: ComponentFixture<CommentEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CommentEditComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CommentEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
