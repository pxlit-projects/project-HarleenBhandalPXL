import { ComponentFixture, TestBed } from '@angular/core/testing';
import { PostDetailComponent } from './post.detail.component';
import { PostService } from '../../services/post/post.service';
import { CommentService } from '../../services/post/comment.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AuthService } from '../../services/auth/auth.service';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

describe('PostDetailComponent', () => {
  let component: PostDetailComponent;
  let fixture: ComponentFixture<PostDetailComponent>;
  let postServiceMock: jasmine.SpyObj<PostService>;
  let commentServiceMock: jasmine.SpyObj<CommentService>;
  let authServiceMock: jasmine.SpyObj<AuthService>;
  let activatedRouteMock: any;

  beforeEach(async () => {
    postServiceMock = jasmine.createSpyObj('PostService', ['getPost']);
    commentServiceMock = jasmine.createSpyObj('CommentService', [
      'getCommentsByPostId',
      'createComment',
      'deleteComment',
    ]);
    authServiceMock = jasmine.createSpyObj('AuthService', ['isAuthenticated']);
    activatedRouteMock = { snapshot: { params: { id: 1 } } };

    postServiceMock.getPost.and.returnValue(of({
      id: 1,
      title: 'Test Post',
      content: 'This is a test post',
      author: 'Test Author',
      creationDate: '2025-01-01',
    }));

    commentServiceMock.getCommentsByPostId.and.returnValue(of([]));

    await TestBed.configureTestingModule({
      imports: [PostDetailComponent, BrowserAnimationsModule],
      providers: [
        { provide: PostService, useValue: postServiceMock },
        { provide: CommentService, useValue: commentServiceMock },
        { provide: AuthService, useValue: authServiceMock },
        { provide: ActivatedRoute, useValue: activatedRouteMock },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(PostDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch post on initialization', () => {
    expect(postServiceMock.getPost).toHaveBeenCalledWith(1);
    expect(component.post).toEqual({
      id: 1,
      title: 'Test Post',
      content: 'This is a test post',
      author: 'Test Author',
      creationDate: '2025-01-01',
    });
  });

  it('should fetch comments on initialization', () => {
    expect(commentServiceMock.getCommentsByPostId).toHaveBeenCalledWith(1);
    expect(component.comments).toEqual([]);
  });
});
