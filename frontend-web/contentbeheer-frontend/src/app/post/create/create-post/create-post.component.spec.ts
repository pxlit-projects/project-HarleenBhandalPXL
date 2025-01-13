import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreatePostComponent } from './create-post.component';
import {PostService} from "../../../services/post/post.service";
import {Router} from "@angular/router";
import { FormsModule } from '@angular/forms';
import {MatFormFieldModule} from "@angular/material/form-field";
import {of, throwError} from "rxjs";
import {MatButtonModule} from "@angular/material/button";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatInputModule} from "@angular/material/input";

describe('CreatePostComponent', () => {
  let component: CreatePostComponent;
  let fixture: ComponentFixture<CreatePostComponent>;
  let postServiceMock: jasmine.SpyObj<PostService>;
  let routerMock: jasmine.SpyObj<Router>;

  beforeEach(async () => {
    postServiceMock = jasmine.createSpyObj('PostService', ['createPost', 'savePostAsConcept']);
    routerMock = jasmine.createSpyObj('Router', ['navigate']);

    await TestBed.configureTestingModule({
      imports: [
        CreatePostComponent,
        FormsModule,
        MatFormFieldModule,
        MatInputModule,
        MatButtonModule,
        BrowserAnimationsModule
      ],
      providers: [
        { provide: PostService, useValue: postServiceMock },
        { provide: Router, useValue: routerMock }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(CreatePostComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize form fields', () => {
    expect(component.title).toBe('');
    expect(component.content).toBe('');
    expect(component.author).toBe('');
  });

  it('should call createPost on valid form submission', () => {
    spyOn(component, 'createPost');
    component.title = 'Test Title';
    component.content = 'Test Content';
    component.author = 'Test Author';
    const form = { valid: true };
    component.onSubmit(form);
    expect(component.createPost).toHaveBeenCalled();
  });

  it('should set errorMessage on invalid form submission', () => {
    const form = { valid: false };
    component.onSubmit(form);
    expect(component.errorMessage).toBe('Form is invalid. Please fill out all required fields.');
  });

  it('should call postService.createPost and navigate on successful post creation', () => {
    const post = { title: 'Test Title', content: 'Test Content', author: 'Test Author' };
    postServiceMock.createPost.and.returnValue(of(post));
    component.title = 'Test Title';
    component.content = 'Test Content';
    component.author = 'Test Author';
    component.createPost();
    expect(postServiceMock.createPost).toHaveBeenCalledWith(post);
    expect(routerMock.navigate).toHaveBeenCalledWith(['/posts']);
  });

  it('should log error on post creation failure', () => {
    const error = new Error('Error creating post');
    spyOn(console, 'error');
    postServiceMock.createPost.and.returnValue(throwError(() => error));
    component.createPost();
    expect(console.error).toHaveBeenCalledWith('Error creating post', error);
  });

  it('should call postService.savePostAsConcept and navigate on successful concept save', () => {
    const post = { title: 'Test Title', content: 'Test Content', author: 'Test Author' };
    postServiceMock.savePostAsConcept.and.returnValue(of(post));
    component.title = 'Test Title';
    component.content = 'Test Content';
    component.author = 'Test Author';
    component.savePostAsConcept();
    expect(postServiceMock.savePostAsConcept).toHaveBeenCalledWith(post);
    expect(routerMock.navigate).toHaveBeenCalledWith(['/concepts']);
  });

  it('should log error on concept save failure', () => {
    const error = new Error('Error saving post as concept');
    spyOn(console, 'error');
    postServiceMock.savePostAsConcept.and.returnValue(throwError(() => error));
    component.savePostAsConcept();
    expect(console.error).toHaveBeenCalledWith('Error saving post as concept', error);
  });

  it('should set errorMessage on createPost failure', () => {
    const error = new Error('Error creating post');
    postServiceMock.createPost.and.returnValue(throwError(() => error));
    component.createPost();
    expect(component.errorMessage).toBe('Error creating post');
  });

  it('should set errorMessage on savePostAsConcept failure', () => {
    const error = new Error('Error saving post as concept');
    postServiceMock.savePostAsConcept.and.returnValue(throwError(() => error));
    component.savePostAsConcept();
    expect(component.errorMessage).toBe('Error saving post as concept');
  });
});
