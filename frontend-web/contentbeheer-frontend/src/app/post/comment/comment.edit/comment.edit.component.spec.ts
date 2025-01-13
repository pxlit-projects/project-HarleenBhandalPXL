import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CommentEditComponent } from './comment.edit.component';
import { CommentService } from '../../../services/post/comment.service';
import { ActivatedRoute, Router } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { of, throwError } from 'rxjs';
import { provideHttpClient } from '@angular/common/http';

describe('CommentEditComponent', () => {
  let component: CommentEditComponent;
  let fixture: ComponentFixture<CommentEditComponent>;
  let commentServiceMock: jasmine.SpyObj<CommentService>;
  let routerMock: jasmine.SpyObj<Router>;
  let activatedRouteMock: any;

  beforeEach(async () => {
    const mockComment = { content: 'Test Comment', postId: 1, userName: 'Test User' };
    commentServiceMock = jasmine.createSpyObj('CommentService', ['getComment', 'updateComment']);
    commentServiceMock.getComment.and.returnValue(of(mockComment));
    commentServiceMock.updateComment.and.returnValue(of(mockComment));

    routerMock = jasmine.createSpyObj('Router', ['navigate']);
    activatedRouteMock = { snapshot: { params: { id: 1 } } };

    await TestBed.configureTestingModule({
      imports: [CommentEditComponent, BrowserAnimationsModule],
      providers: [
        { provide: CommentService, useValue: commentServiceMock },
        { provide: Router, useValue: routerMock },
        { provide: ActivatedRoute, useValue: activatedRouteMock },
        provideHttpClient(),
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(CommentEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });


  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call getComment on initialization', () => {
    spyOn(component, 'getComment').and.callThrough();
    component.ngOnInit();
    expect(component.getComment).toHaveBeenCalled();
  });

  it('should set comment and postId correctly in getComment', () => {
    const mockComment = { content: 'Test Comment', postId: 1, userName: 'Test User' };
    commentServiceMock.getComment.and.returnValue(of(mockComment));
    component.getComment();
    expect(component.comment).toEqual('Test Comment');
    expect(component.postId).toEqual(1);
  });

  it('should log error on getComment failure', () => {
    const error = new Error('Error fetching comment');
    spyOn(console, 'error');
    commentServiceMock.getComment.and.returnValue(throwError(() => error));
    component.getComment();
    expect(console.error).toHaveBeenCalledWith('Error fetching comment', error);
  });

  it('should call editComment on valid form submission', () => {
    spyOn(component, 'editComment');
    const form = { valid: true };
    component.onSubmit(form);
    expect(component.editComment).toHaveBeenCalled();
  });

  it('should set errorMessage on invalid form submission', () => {
    const form = { valid: false };
    component.onSubmit(form);
    expect(component.errorMessage).toBe('Form is invalid. Please fill out all required fields.');
  });

  it('should call commentService.updateComment and navigate on successful comment update', () => {
    const mockComment = { content: 'Updated Comment', postId: 1, userName: 'Test User' };
    commentServiceMock.updateComment.and.returnValue(of(mockComment));
    component.editComment();
    expect(commentServiceMock.updateComment).toHaveBeenCalledWith(1, component.comment);
    expect(routerMock.navigate).toHaveBeenCalledWith(['/posts', 1]);
  });

  it('should log error on comment update failure', () => {
    const error = new Error('Error updating comment');
    spyOn(console, 'error');
    commentServiceMock.updateComment.and.returnValue(throwError(() => error));
    component.editComment();
    expect(console.error).toHaveBeenCalledWith('Error updating comment', error);
  });
});
