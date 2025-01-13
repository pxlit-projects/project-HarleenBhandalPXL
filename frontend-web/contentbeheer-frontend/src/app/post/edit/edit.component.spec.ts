import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { EditComponent } from './edit.component';
import { PostService } from '../../services/post/post.service';
import { ActivatedRoute, Router } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { of, throwError } from 'rxjs';

describe('EditComponent', () => {
  let component: EditComponent;
  let fixture: ComponentFixture<EditComponent>;
  let postServiceMock: jasmine.SpyObj<PostService>;
  let routerMock: jasmine.SpyObj<Router>;
  let activatedRouteMock: any;

  beforeEach(async () => {
    postServiceMock = jasmine.createSpyObj('PostService', ['getPost', 'editPost']);
    routerMock = jasmine.createSpyObj('Router', ['navigate']);
    activatedRouteMock = { snapshot: { params: { id: 1 } } };

    postServiceMock.getPost.and.returnValue(of({
      title: 'Mock Title',
      content: 'Mock Content',
      author: 'Mock Author'
    }));

    await TestBed.configureTestingModule({
      imports: [EditComponent, BrowserAnimationsModule],
      providers: [
        provideHttpClient(),
        { provide: PostService, useValue: postServiceMock },
        { provide: Router, useValue: routerMock },
        { provide: ActivatedRoute, useValue: activatedRouteMock }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(EditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });


  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch post on initialization', () => {
    const mockPost = { title: 'Test Title', content: 'Test Content', author: 'Test Author' };
    postServiceMock.getPost.and.returnValue(of(mockPost));
    component.ngOnInit();
    expect(postServiceMock.getPost).toHaveBeenCalledWith(1);
    expect(component.title).toBe('Test Title');
    expect(component.content).toBe('Test Content');
    expect(component.author).toBe('Test Author');
  });

  it('should log error if no postId in URL', () => {
    spyOn(console, 'error');
    activatedRouteMock.snapshot.params = {};
    component.ngOnInit();
    expect(console.error).toHaveBeenCalledWith('No id found in the URL');
  });

  it('should update post successfully', () => {
    const mockPost = { title: 'Updated Title', content: 'Updated Content', author: 'Updated Author' };
    postServiceMock.editPost.and.returnValue(of(mockPost));
    component.title = 'Updated Title';
    component.content = 'Updated Content';
    component.author = 'Updated Author';
    component.postId = 1;
    component.editPost();
    expect(postServiceMock.editPost).toHaveBeenCalledWith(1, mockPost);
    expect(routerMock.navigate).toHaveBeenCalledWith(['/posts']);
  });

  it('should log error on post update failure', () => {
    const error = new Error('Error updating post');
    spyOn(console, 'error');
    postServiceMock.editPost.and.returnValue(throwError(() => error));
    component.editPost();
    expect(console.error).toHaveBeenCalledWith('Error updating post', error);
  });

  it('should set errorMessage on invalid form submission', () => {
    const form = { valid: false };
    component.onSubmit(form);
    expect(component.errorMessage).toBe('Form is invalid. Please fill out all required fields.');
  });
});
