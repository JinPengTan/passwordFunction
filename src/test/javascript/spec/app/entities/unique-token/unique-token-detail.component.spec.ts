import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { UniqueTokenDetailComponent } from 'app/entities/unique-token/unique-token-detail.component';
import { UniqueToken } from 'app/shared/model/unique-token.model';

describe('Component Tests', () => {
  describe('UniqueToken Management Detail Component', () => {
    let comp: UniqueTokenDetailComponent;
    let fixture: ComponentFixture<UniqueTokenDetailComponent>;
    const route = ({ data: of({ uniqueToken: new UniqueToken(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [UniqueTokenDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UniqueTokenDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UniqueTokenDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load uniqueToken on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.uniqueToken).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
