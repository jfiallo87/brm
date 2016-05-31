'use strict';

describe('Controller Tests', function() {

    describe('FacilityCatalog Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFacilityCatalog, MockFacilityEventCatalog, MockFacilitySchedule;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFacilityCatalog = jasmine.createSpy('MockFacilityCatalog');
            MockFacilityEventCatalog = jasmine.createSpy('MockFacilityEventCatalog');
            MockFacilitySchedule = jasmine.createSpy('MockFacilitySchedule');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'FacilityCatalog': MockFacilityCatalog,
                'FacilityEventCatalog': MockFacilityEventCatalog,
                'FacilitySchedule': MockFacilitySchedule
            };
            createController = function() {
                $injector.get('$controller')("FacilityCatalogDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'brmApp:facilityCatalogUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
