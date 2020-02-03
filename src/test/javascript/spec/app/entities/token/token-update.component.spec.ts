import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { TokenUpdateComponent } from 'app/entities/token/token-update.component';
import { TokenService } from 'app/entities/token/token.service';
import { Token } from 'app/shared/model/token.model';

describe('Component Tests', () => {
  describe('Token Management Update Component', () => {
    let comp: TokenUpdateComponent;
    let fixture: ComponentFixture<TokenUpdateComponent>;
    let service: TokenService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [TokenUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TokenUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TokenUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TokenService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Token(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Token();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
