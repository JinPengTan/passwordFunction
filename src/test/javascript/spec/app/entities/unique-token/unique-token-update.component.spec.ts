import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { UniqueTokenUpdateComponent } from 'app/entities/unique-token/unique-token-update.component';
import { UniqueTokenService } from 'app/entities/unique-token/unique-token.service';
import { UniqueToken } from 'app/shared/model/unique-token.model';

describe('Component Tests', () => {
  describe('UniqueToken Management Update Component', () => {
    let comp: UniqueTokenUpdateComponent;
    let fixture: ComponentFixture<UniqueTokenUpdateComponent>;
    let service: UniqueTokenService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [UniqueTokenUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(UniqueTokenUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UniqueTokenUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UniqueTokenService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UniqueToken(123);
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
        const entity = new UniqueToken();
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
