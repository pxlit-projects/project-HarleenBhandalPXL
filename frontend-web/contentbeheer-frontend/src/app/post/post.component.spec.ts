import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostComponent } from './post.component';
import {PostService} from "../services/post/post.service";
import {Post} from "../models/post.model";
import {Router} from "@angular/router";
import {Filter} from "../models/filter.model";
import {of} from "rxjs";
import { provideNativeDateAdapter } from '@angular/material/core';

describe('PostComponent', () => {
  let component: PostComponent;
  let fixture: ComponentFixture<PostComponent>;
  let postServiceMock: jasmine.SpyObj<PostService>;
  let routerMock: jasmine.SpyObj<Router>;

  const mockPosts: Post[] = [
    new Post(1, 'Post 1', 'Content 1', 'Author 1', "2024-01-01"),
    new Post(2, 'Post 2', 'Content 2', 'Author 2', "2024-01-02"),
  ]

  beforeEach(() => {
    postServiceMock = jasmine.createSpyObj('PostService', ['getPublishedPosts']);
    routerMock = jasmine.createSpyObj('Router', ['navigate']);

    TestBed.configureTestingModule({
      imports: [ PostComponent ],
      providers: [
        { provide: PostService, useValue: postServiceMock },
        { provide: Router, useValue: routerMock },
        provideNativeDateAdapter()
      ]
    });

    fixture = TestBed.createComponent(PostComponent);
    component = fixture.componentInstance;
  })

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should call getPosts on initialization', () => {
    spyOn(component, 'getPosts');
    component.ngOnInit();
    expect(component.getPosts).toHaveBeenCalled();
  });

  it('should set posts and filteredData correctly in getPosts', () => {
    postServiceMock.getPublishedPosts.and.returnValue(of(mockPosts));
    component.getPosts();
    expect(component.posts).toEqual(mockPosts);
    expect(component.filteredData).toEqual(mockPosts);
  });

  it('should navigate to the correct route in navigateToPost', () => {
    const postId = 1;
    component.navigateToPost(postId);
    expect(routerMock.navigate).toHaveBeenCalledWith(['posts', postId]);
  });

  it('should filter posts correctly in handleFilter', () => {
    component.posts = mockPosts;
    const filter: Filter = { author: 'Author 1', content: '', date: undefined };
    component.handleFilter(filter);
    expect(component.filteredData).toEqual([mockPosts[0]]);
  });

  it('should return true for matching filter in isPostMatchingFilter', () => {
    const post = new Post(1, 'Post 1', 'Content 1', 'Author 1', "2024-01-01");
    const filter: Filter = { author: 'Author 1', content: 'Content 1', date: new Date("2024-01-01") };
    expect(component.isPostMatchingFilter(post, filter)).toBeTrue();
  });

  it('should return false for non-matching filter in isPostMatchingFilter', () => {
    const post = new Post(1, 'Post 1', 'Content 1', 'Author 1', "2024-01-01");
    const filter: Filter = { author: 'Author 2', content: 'Content 2', date: new Date("2024-01-02") };
    expect(component.isPostMatchingFilter(post, filter)).toBeFalse();
  });

  it('should handle empty filter correctly in handleFilter', () => {
    component.posts = mockPosts;
    const filter: Filter = { author: '', content: '', date: undefined };
    component.handleFilter(filter);
    expect(component.filteredData).toEqual(mockPosts);
  });
});
