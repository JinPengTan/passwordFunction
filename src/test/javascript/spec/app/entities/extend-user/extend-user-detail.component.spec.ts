import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { ExtendUserDetailComponent } from 'app/entities/extend-user/extend-user-detail.component';
import { ExtendUser } from 'app/shared/model/extend-user.model';

describe('Component Tests', () => {
  describe('ExtendUser Management Detail Component', () => {
    let comp: ExtendUserDetailComponent;
    let fixture: ComponentFixture<ExtendUserDetailComponent>;
    const route = ({ data: of({ extendUser: new ExtendUser(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [ExtendUserDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ExtendUserDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ExtendUserDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load extendUser on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.extendUser).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
