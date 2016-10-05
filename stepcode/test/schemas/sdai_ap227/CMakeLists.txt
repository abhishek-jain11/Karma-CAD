# -----  GENERATED FILE  -----
# -----   Do not edit!   -----

# schema name: plant_spatial_configuration
# (short name: sdai_ap227)
# 333 entities, 49 types

# targets, logic, etc are within a set of macros shared by all schemas
include(${SC_CMAKE_DIR}/SC_CXX_schema_macros.cmake)
set(sdai_ap227_file_count 774)

PROJECT(sdai_ap227)
# list headers so they can be installed - entity, type, misc
set(sdai_ap227_entity_hdrs
     entity/SdaiDocument_usage_constraint.h                                      entity/SdaiReparametrised_composite_curve_segment.h                         
     entity/SdaiDucting_system.h                                                 entity/SdaiGroup_relationship.h                                             
     entity/SdaiKnown_source.h                                                   entity/SdaiAxis1_placement.h                                                
     entity/SdaiBezier_curve.h                                                   entity/SdaiProcess_capability.h                                             
     entity/SdaiTime_measure_with_unit.h                                         entity/SdaiDate_and_time.h                                                  
     entity/SdaiDimensional_characteristic_representation.h                      entity/SdaiPlant_spatial_configuration_date_assignment.h                    
     entity/SdaiProduct_definition_formation_with_specified_source.h             entity/SdaiRevolved_area_solid.h                                            
     entity/SdaiSurface_replica.h                                                entity/SdaiColour.h                                                         
     entity/SdaiPlant_line_segment_termination.h                                 entity/SdaiMaterial_designation.h                                           
     entity/SdaiMaterial_property.h                                              entity/SdaiAmount_of_substance_measure_with_unit.h                          
     entity/SdaiHybrid_shape_representation.h                                    entity/SdaiOrganization_assignment.h                                        
     entity/SdaiRepresentation.h                                                 entity/SdaiFaceted_brep.h                                                   
     entity/SdaiMass_unit.h                                                      entity/SdaiAmount_of_substance_unit.h                                       
     entity/SdaiChange_from_assignment.h                                         entity/SdaiElectric_current_unit.h                                          
     entity/SdaiSolid_angle_unit.h                                               entity/SdaiRectangular_composite_surface.h                                  
     entity/SdaiPerson_and_organization_role.h                                   entity/SdaiToroidal_surface.h                                               
     entity/SdaiGeometric_set_replica.h                                          entity/SdaiAction_status.h                                                  
     entity/SdaiApproval_role.h                                                  entity/SdaiComposite_curve.h                                                
     entity/SdaiValve_classification.h                                           entity/SdaiB_spline_curve_with_knots.h                                      
     entity/SdaiGlobal_unit_assigned_context.h                                   entity/SdaiPlant_spatial_configuration_person_assignment.h                  
     entity/SdaiShape_aspect_relationship.h                                      entity/SdaiChange_action.h                                                  
     entity/SdaiFace_surface.h                                                   entity/SdaiProperty_definition_representation.h                             
     entity/SdaiB_spline_surface_with_knots.h                                    entity/SdaiSurface_patch.h                                                  
     entity/SdaiSite_building.h                                                  entity/SdaiAction_request_status.h                                          
     entity/SdaiArea_measure_with_unit.h                                         entity/SdaiPerson.h                                                         
     entity/SdaiPlant_line_definition.h                                          entity/SdaiQuasi_uniform_surface.h                                          
     entity/SdaiOriented_edge.h                                                  entity/SdaiCartesian_transformation_operator_3d.h                           
     entity/SdaiPlant_spatial_configuration_date_and_time_assignment.h           entity/SdaiDegenerate_pcurve.h                                              
     entity/SdaiStream_phase.h                                                   entity/SdaiAction_relationship.h                                            
     entity/SdaiElectrical_system.h                                              entity/SdaiPlant_item_interference.h                                        
     entity/SdaiGeometric_set.h                                                  entity/SdaiUniform_surface.h                                                
     entity/SdaiSwage_fitting_classification.h                                   entity/SdaiBounded_surface_curve.h                                          
     entity/SdaiHeat_tracing_representation.h                                    entity/SdaiMeasure_representation_item.h                                    
     entity/SdaiHyperbola.h                                                      entity/SdaiPlant_spatial_configuration_approval_assignment.h                
     entity/SdaiProperty_definition.h                                            entity/SdaiCatalogue_connector.h                                            
     entity/SdaiType_qualifier.h                                                 entity/SdaiDegenerate_toroidal_surface.h                                    
     entity/SdaiProduct_definition_with_associated_documents.h                   entity/SdaiMaterial_property_representation.h                               
     entity/SdaiPipe_classification.h                                            entity/SdaiPath.h                                                           
     entity/SdaiRight_circular_cone.h                                            entity/SdaiApproval.h                                                       
     entity/SdaiPoly_loop.h                                                      entity/SdaiTopological_representation_item.h                                
     entity/SdaiManifold_solid_brep.h                                            entity/SdaiPoint_on_surface.h                                               
     entity/SdaiRational_b_spline_curve.h                                        entity/SdaiComposite_curve_on_surface.h                                     
     entity/SdaiGroup.h                                                          entity/SdaiPerson_role.h                                                    
     entity/SdaiPolyline.h                                                       entity/SdaiPre_defined_item.h                                               
     entity/SdaiSpherical_surface.h                                              entity/SdaiDerived_shape_aspect.h                                           
     entity/SdaiSystem_space.h                                                   entity/SdaiBounded_surface.h                                                
     entity/SdaiDate_role.h                                                      entity/SdaiSystem_classification.h                                          
     entity/SdaiApplication_context_element.h                                    entity/SdaiRepresentation_context.h                                         
     entity/SdaiConnection_motion_classification.h                               entity/SdaiShape_definition_representation.h                                
     entity/SdaiSite_representation.h                                            entity/SdaiClosed_shell.h                                                   
     entity/SdaiShape_representation.h                                           entity/SdaiApplication_protocol_definition.h                                
     entity/SdaiB_spline_surface.h                                               entity/SdaiProduct_related_product_category.h                               
     entity/SdaiElbow_fitting_classification.h                                   entity/SdaiBlank_fitting_classification.h                                   
     entity/SdaiAction_method.h                                                  entity/SdaiGroup_assignment.h                                               
     entity/SdaiProduct_definition_shape.h                                       entity/SdaiSeam_curve.h                                                     
     entity/SdaiPiping_system.h                                                  entity/SdaiContext_dependent_unit.h                                         
     entity/SdaiExecuted_action.h                                                entity/SdaiPiping_connector_classification.h                                
     entity/SdaiProduct_definition_formation_relationship.h                      entity/SdaiSwept_area_solid.h                                               
     entity/SdaiEdge.h                                                           entity/SdaiExternally_defined_item.h                                        
     entity/SdaiProduct_material_composition_relationship.h                      entity/SdaiMapped_item.h                                                    
     entity/SdaiDate_assignment.h                                                entity/SdaiSurface_of_revolution.h                                          
     entity/SdaiVector.h                                                         entity/SdaiCentre_of_symmetry.h                                             
     entity/SdaiData_environment.h                                               entity/SdaiSolid_angle_measure_with_unit.h                                  
     entity/SdaiPerson_and_organization_assignment.h                             entity/SdaiLine_plant_item_connection.h                                     
     entity/SdaiRatio_unit.h                                                     entity/SdaiDerived_unit_element.h                                           
     entity/SdaiFlange_fitting_neck_type_classification.h                        entity/SdaiTrimmed_curve.h                                                  
     entity/SdaiVertex.h                                                         entity/SdaiComposite_curve_segment.h                                        
     entity/SdaiProduct_definition_usage.h                                       entity/SdaiProduct_category_relationship.h                                  
     entity/SdaiLoop.h                                                           entity/SdaiAction_request_assignment.h                                      
     entity/SdaiDimensional_size.h                                               entity/SdaiOrganization.h                                                   
     entity/SdaiDerived_unit.h                                                   entity/SdaiElectrical_connector_classification.h                            
     entity/SdaiShape_dimension_representation.h                                 entity/SdaiCartesian_point.h                                                
     entity/SdaiElectric_current_measure_with_unit.h                             entity/SdaiEllipse.h                                                        
     entity/SdaiCatalogue.h                                                      entity/SdaiConic.h                                                          
     entity/SdaiSite.h                                                           entity/SdaiEvaluated_degenerate_pcurve.h                                    
     entity/SdaiPlane_angle_unit.h                                               entity/SdaiThermodynamic_temperature_measure_with_unit.h                    
     entity/SdaiCatalogue_item.h                                                 entity/SdaiShell_based_wireframe_model.h                                    
     entity/SdaiApplication_context.h                                            entity/SdaiElementary_surface.h                                             
     entity/SdaiOpen_shell.h                                                     entity/SdaiConnected_face_set.h                                             
     entity/SdaiAxis2_placement_3d.h                                             entity/SdaiCurve_replica.h                                                  
     entity/SdaiDesign_project_assignment.h                                      entity/SdaiSurface_curve.h                                                  
     entity/SdaiCalendar_date.h                                                  entity/SdaiDescriptive_colour.h                                             
     entity/SdaiEdge_loop.h                                                      entity/SdaiPlant_spatial_configuration_action_request_assignment.h          
     entity/SdaiProduct_definition_substitute.h                                  entity/SdaiOriented_closed_shell.h                                          
     entity/SdaiAdvanced_csg_shape_representation.h                              entity/SdaiPlant_item_connection.h                                          
     entity/SdaiExternally_defined_plant_item_definition.h                       entity/SdaiFace_bound.h                                                     
     entity/SdaiLine_less_piping_system.h                                        entity/SdaiPlant_spatial_configuration_person_and_organization_assignment.h 
     entity/SdaiOrganization_role.h                                              entity/SdaiOriented_face.h                                                  
     entity/SdaiSolid_model.h                                                    entity/SdaiFace.h                                                           
     entity/SdaiProduct_definition.h                                             entity/SdaiSwept_surface.h                                                  
     entity/SdaiConnection_functional_classification.h                           entity/SdaiBounded_pcurve.h                                                 
     entity/SdaiBoolean_result.h                                                 entity/SdaiRepresentation_item.h                                            
     entity/SdaiAction_method_relationship.h                                     entity/SdaiPlane.h                                                          
     entity/SdaiCircle.h                                                         entity/SdaiLine.h                                                           
     entity/SdaiDocument.h                                                       entity/SdaiDate_and_time_assignment.h                                       
     entity/SdaiProduct.h                                                        entity/SdaiPurchase_assignment.h                                            
     entity/SdaiStructural_load_connector_classification.h                       entity/SdaiChange_to_assignment.h                                           
     entity/SdaiMaterial_designation_characterization.h                          entity/SdaiPlacement.h                                                      
     entity/SdaiPresentation_layer_assignment.h                                  entity/SdaiB_spline_curve.h                                                 
     entity/SdaiConnector_end_type_classification.h                              entity/SdaiDocument_relationship.h                                          
     entity/SdaiPcurve.h                                                         entity/SdaiVersioned_action_request.h                                       
     entity/SdaiOrganizational_project.h                                         entity/SdaiMeasure_with_unit.h                                              
     entity/SdaiRectangular_trimmed_surface.h                                    entity/SdaiCurve.h                                                          
     entity/SdaiDate.h                                                           entity/SdaiDefinitional_representation.h                                    
     entity/SdaiThermodynamic_temperature_unit.h                                 entity/SdaiApproval_person_organization.h                                   
     entity/SdaiCurve_bounded_surface.h                                          entity/SdaiParabola.h                                                       
     entity/SdaiPlant.h                                                          entity/SdaiQuasi_uniform_curve.h                                            
     entity/SdaiUniform_curve.h                                                  entity/SdaiGeometric_curve_set.h                                            
     entity/SdaiInline_equipment.h                                               entity/SdaiLength_unit.h                                                    
     entity/SdaiColour_rgb.h                                                     entity/SdaiBoundary_curve.h                                                 
     entity/SdaiLength_measure_with_unit.h                                       entity/SdaiProduct_category.h                                               
     entity/SdaiRational_b_spline_surface.h                                      entity/SdaiTime_unit.h                                                      
     entity/SdaiCartesian_transformation_operator.h                              entity/SdaiLocal_time.h                                                     
     entity/SdaiExternal_source.h                                                entity/SdaiName_assignment.h                                                
     entity/SdaiConversion_based_unit.h                                          entity/SdaiPiping_component_class.h                                         
     entity/SdaiProduct_definition_formation.h                                   entity/SdaiConnection_node.h                                                
     entity/SdaiDimensional_exponents.h                                          entity/SdaiLine_plant_item_branch_connection.h                              
     entity/SdaiPiping_component_definition.h                                    entity/SdaiProduct_context.h                                                
     entity/SdaiAssembly_component_usage.h                                       entity/SdaiDimensional_location.h                                           
     entity/SdaiOffset_surface.h                                                 entity/SdaiGeometric_representation_context.h                               
     entity/SdaiMass_measure_with_unit.h                                         entity/SdaiPlant_item_weight_representation.h                               
     entity/SdaiSi_unit.h                                                        entity/SdaiInterfering_shape_element.h                                      
     entity/SdaiSurface.h                                                        entity/SdaiPlant_line_segment_definition.h                                  
     entity/SdaiLine_branch_connection.h                                         entity/SdaiPlant_item_connector.h                                           
     entity/SdaiOffset_curve_2d.h                                                entity/SdaiDesign_project.h                                                 
     entity/SdaiPipe_closure_fitting_classification.h                            entity/SdaiAction_assignment.h                                              
     entity/SdaiApproval_assignment.h                                            entity/SdaiHalf_space_solid.h                                               
     entity/SdaiTorus.h                                                          entity/SdaiDocument_reference.h                                             
     entity/SdaiReference_geometry.h                                             entity/SdaiRevolved_face_solid.h                                            
     entity/SdaiPlane_angle_measure_with_unit.h                                  entity/SdaiShape_aspect.h                                                   
     entity/SdaiVolume_measure_with_unit.h                                       entity/SdaiBounded_curve.h                                                  
     entity/SdaiPlant_spatial_configuration_organization_assignment.h            entity/SdaiRatio_measure_with_unit.h                                        
     entity/SdaiClassification_assignment.h                                      entity/SdaiReserved_space.h                                                 
     entity/SdaiRight_circular_cylinder.h                                        entity/SdaiAction_request_solution.h                                        
     entity/SdaiAxis2_placement_2d.h                                             entity/SdaiDirected_action.h                                                
     entity/SdaiPoint.h                                                          entity/SdaiPoint_replica.h                                                  
     entity/SdaiCoordinated_universal_time_offset.h                              entity/SdaiFunctionally_defined_transformation.h                            
     entity/SdaiLine_termination_connection.h                                    entity/SdaiDirection.h                                                      
     entity/SdaiApproval_status.h                                                entity/SdaiOriented_path.h                                                  
     entity/SdaiRepresentation_map.h                                             entity/SdaiPlant_spatial_configuration_change_assignment.h                  
     entity/SdaiVolume_unit.h                                                    entity/SdaiBezier_surface.h                                                 
     entity/SdaiCylindrical_surface.h                                            entity/SdaiPerson_and_organization.h                                        
     entity/SdaiBrep_with_voids.h                                                entity/SdaiChange_item_id_assignment.h                                      
     entity/SdaiStructural_system.h                                              entity/SdaiSurface_of_linear_extrusion.h                                    
     entity/SdaiMake_from_usage_option.h                                         entity/SdaiPlant_spatial_configuration_document_reference.h                 
     entity/SdaiProduct_definition_context.h                                     entity/SdaiExtruded_face_solid.h                                            
     entity/SdaiProduct_definition_relationship.h                                entity/SdaiSpecialty_item_classification.h                                  
     entity/SdaiPerson_assignment.h                                              entity/SdaiExternally_defined_classification.h                              
     entity/SdaiReducer_fitting_classification.h                                 entity/SdaiSite_feature.h                                                   
     entity/SdaiQualified_representation_item.h                                  entity/SdaiApproval_date_time.h                                             
     entity/SdaiPoint_on_curve.h                                                 entity/SdaiGeometric_representation_item.h                                  
     entity/SdaiIntersection_curve.h                                             entity/SdaiSwept_face_solid.h                                               
     entity/SdaiAction_directive.h                                               entity/SdaiCsg_solid.h                                                      
     entity/SdaiOffset_curve_3d.h                                                entity/SdaiSupport_constraint_representation.h                              
     entity/SdaiArea_unit.h                                                      entity/SdaiOriented_open_shell.h                                            
     entity/SdaiDate_time_role.h                                                 entity/SdaiDescriptive_representation_item.h                                
     entity/SdaiDocument_type.h                                                  entity/SdaiProperty_definition_relationship.h                               
     entity/SdaiInstrumentation_and_control_system.h                             entity/SdaiAction.h                                                         
     entity/SdaiSphere.h                                                         entity/SdaiConical_surface.h                                                
     entity/SdaiSpacer_fitting_classification.h                                  entity/SdaiStream_design_case.h                                             
     entity/SdaiRequired_material_property.h                                     entity/SdaiEdge_curve.h                                                     
     entity/SdaiPlant_item_route.h                                               entity/SdaiFlange_fitting_classification.h                                  
     entity/SdaiAngular_location.h                                               entity/SdaiBlock.h                                                          
     entity/SdaiNamed_unit.h                                                     entity/SdaiCharacterized_object.h                                           
     entity/SdaiShape_aspect_deriving_relationship.h                             entity/SdaiColour_specification.h                                           
     entity/SdaiExtruded_area_solid.h                                            entity/SdaiPiping_design_csg_shape_representation.h                         
     entity/SdaiTruncated_pyramid.h                                                 )

set(sdai_ap227_type_hdrs
     type/SdaiBoolean_operator_var.h                                             type/SdaiClassification_item.h                                              
     type/SdaiCsg_select.h                                                       type/SdaiPlant_spatial_configuration_person_item.h                          
     type/SdaiAhead_or_behind_var.h                                              type/SdaiPcurve_or_surface.h                                                
     type/SdaiPlant_spatial_configuration_person_and_organization_item.h         type/SdaiVector_or_direction.h                                              
     type/SdaiChange_delta_item.h                                                type/SdaiSource_item.h                                                      
     type/SdaiDate_time_select.h                                                 type/SdaiPerson_organization_select.h                                       
     type/SdaiTrimming_preference_var.h                                          type/SdaiCurve_on_surface.h                                                 
     type/SdaiPreferred_surface_curve_representation_var.h                       type/SdaiCsg_primitive.h                                                    
     type/SdaiTransition_code_var.h                                              type/SdaiGeometric_set_select.h                                             
     type/SdaiShape_definition.h                                                 type/SdaiSi_prefix_var.h                                                    
     type/SdaiApproval_item.h                                                    type/SdaiPlant_spatial_configuration_date_and_time_item.h                   
     type/SdaiPlant_spatial_configuration_action_request_item.h                  type/SdaiDesign_project_item.h                                              
     type/SdaiChange_item.h                                                      type/SdaiLayered_item.h                                                     
     type/SdaiCharacterized_product_definition.h                                 type/SdaiShell.h                                                            
     type/SdaiPurchase_item.h                                                    type/SdaiMeasure_value.h                                                    
     type/SdaiSi_unit_name_var.h                                                 type/SdaiB_spline_curve_form_var.h                                          
     type/SdaiCharacterized_definition.h                                         type/SdaiB_spline_surface_form_var.h                                        
     type/SdaiBoolean_operand.h                                                  type/SdaiPlant_spatial_configuration_document_item.h                        
     type/SdaiDimensional_characteristic.h                                       type/SdaiAngle_relator_var.h                                                
     type/SdaiCharacterized_material_property.h                                  type/SdaiPlant_spatial_configuration_dated_item.h                           
     type/SdaiSource_var.h                                                       type/SdaiUnit.h                                                             
     type/SdaiReversible_topology.h                                              type/SdaiAxis2_placement.h                                                  
     type/SdaiTrimming_select.h                                                  type/SdaiReversible_topology_item.h                                         
     type/SdaiPlant_spatial_configuration_organization_item.h                    type/SdaiValue_qualifier.h                                                  
     type/SdaiKnot_type_var.h                                                       )

set(sdai_ap227_misc_hdrs
     Sdaiclasses.h   schema.h
     SdaiPLANT_SPATIAL_CONFIGURATIONNames.h
     SdaiPLANT_SPATIAL_CONFIGURATION.h
   )

# install all headers
set(all_headers ${sdai_ap227_entity_hdrs} ${sdai_ap227_type_hdrs} ${sdai_ap227_misc_hdrs})
foreach( header_file ${all_headers} )
  set(curr_dir)
  get_filename_component(curr_dir ${header_file} PATH)
  get_filename_component(curr_name ${header_file} NAME)
  if (curr_dir)
    install( FILES ${header_file} DESTINATION "include/schemas/sdai_ap227/${curr_dir}" )
  else (curr_dir)
    install( FILES ${header_file} DESTINATION "include/schemas/sdai_ap227" )
  endif (curr_dir)
endforeach()
# implementation files - 3 lists

# unity build: #include small .cc files to reduce the number
# of translation units that must be compiled
if(SC_UNITY_BUILD)
  # turns off include statements within type and entity .cc's - the unity T.U.'s include a unity header
  add_definitions( -DSC_SDAI_UNITY_BUILD)
  set(sdai_ap227_entity_impls SdaiPLANT_SPATIAL_CONFIGURATION_unity_entities.cc)
  set(sdai_ap227_type_impls SdaiPLANT_SPATIAL_CONFIGURATION_unity_types.cc)
else(SC_UNITY_BUILD)
  set(sdai_ap227_entity_impls
     entity/SdaiDocument_usage_constraint.cc                                     entity/SdaiReparametrised_composite_curve_segment.cc                        
     entity/SdaiDucting_system.cc                                                entity/SdaiGroup_relationship.cc                                            
     entity/SdaiKnown_source.cc                                                  entity/SdaiAxis1_placement.cc                                               
     entity/SdaiBezier_curve.cc                                                  entity/SdaiProcess_capability.cc                                            
     entity/SdaiTime_measure_with_unit.cc                                        entity/SdaiDate_and_time.cc                                                 
     entity/SdaiDimensional_characteristic_representation.cc                     entity/SdaiPlant_spatial_configuration_date_assignment.cc                   
     entity/SdaiProduct_definition_formation_with_specified_source.cc            entity/SdaiRevolved_area_solid.cc                                           
     entity/SdaiSurface_replica.cc                                               entity/SdaiColour.cc                                                        
     entity/SdaiPlant_line_segment_termination.cc                                entity/SdaiMaterial_designation.cc                                          
     entity/SdaiMaterial_property.cc                                             entity/SdaiAmount_of_substance_measure_with_unit.cc                         
     entity/SdaiHybrid_shape_representation.cc                                   entity/SdaiOrganization_assignment.cc                                       
     entity/SdaiRepresentation.cc                                                entity/SdaiFaceted_brep.cc                                                  
     entity/SdaiMass_unit.cc                                                     entity/SdaiAmount_of_substance_unit.cc                                      
     entity/SdaiChange_from_assignment.cc                                        entity/SdaiElectric_current_unit.cc                                         
     entity/SdaiSolid_angle_unit.cc                                              entity/SdaiRectangular_composite_surface.cc                                 
     entity/SdaiPerson_and_organization_role.cc                                  entity/SdaiToroidal_surface.cc                                              
     entity/SdaiGeometric_set_replica.cc                                         entity/SdaiAction_status.cc                                                 
     entity/SdaiApproval_role.cc                                                 entity/SdaiComposite_curve.cc                                               
     entity/SdaiValve_classification.cc                                          entity/SdaiB_spline_curve_with_knots.cc                                     
     entity/SdaiGlobal_unit_assigned_context.cc                                  entity/SdaiPlant_spatial_configuration_person_assignment.cc                 
     entity/SdaiShape_aspect_relationship.cc                                     entity/SdaiChange_action.cc                                                 
     entity/SdaiFace_surface.cc                                                  entity/SdaiProperty_definition_representation.cc                            
     entity/SdaiB_spline_surface_with_knots.cc                                   entity/SdaiSurface_patch.cc                                                 
     entity/SdaiSite_building.cc                                                 entity/SdaiAction_request_status.cc                                         
     entity/SdaiArea_measure_with_unit.cc                                        entity/SdaiPerson.cc                                                        
     entity/SdaiPlant_line_definition.cc                                         entity/SdaiQuasi_uniform_surface.cc                                         
     entity/SdaiOriented_edge.cc                                                 entity/SdaiCartesian_transformation_operator_3d.cc                          
     entity/SdaiPlant_spatial_configuration_date_and_time_assignment.cc          entity/SdaiDegenerate_pcurve.cc                                             
     entity/SdaiStream_phase.cc                                                  entity/SdaiAction_relationship.cc                                           
     entity/SdaiElectrical_system.cc                                             entity/SdaiPlant_item_interference.cc                                       
     entity/SdaiGeometric_set.cc                                                 entity/SdaiUniform_surface.cc                                               
     entity/SdaiSwage_fitting_classification.cc                                  entity/SdaiBounded_surface_curve.cc                                         
     entity/SdaiHeat_tracing_representation.cc                                   entity/SdaiMeasure_representation_item.cc                                   
     entity/SdaiHyperbola.cc                                                     entity/SdaiPlant_spatial_configuration_approval_assignment.cc               
     entity/SdaiProperty_definition.cc                                           entity/SdaiCatalogue_connector.cc                                           
     entity/SdaiType_qualifier.cc                                                entity/SdaiDegenerate_toroidal_surface.cc                                   
     entity/SdaiProduct_definition_with_associated_documents.cc                  entity/SdaiMaterial_property_representation.cc                              
     entity/SdaiPipe_classification.cc                                           entity/SdaiPath.cc                                                          
     entity/SdaiRight_circular_cone.cc                                           entity/SdaiApproval.cc                                                      
     entity/SdaiPoly_loop.cc                                                     entity/SdaiTopological_representation_item.cc                               
     entity/SdaiManifold_solid_brep.cc                                           entity/SdaiPoint_on_surface.cc                                              
     entity/SdaiRational_b_spline_curve.cc                                       entity/SdaiComposite_curve_on_surface.cc                                    
     entity/SdaiGroup.cc                                                         entity/SdaiPerson_role.cc                                                   
     entity/SdaiPolyline.cc                                                      entity/SdaiPre_defined_item.cc                                              
     entity/SdaiSpherical_surface.cc                                             entity/SdaiDerived_shape_aspect.cc                                          
     entity/SdaiSystem_space.cc                                                  entity/SdaiBounded_surface.cc                                               
     entity/SdaiDate_role.cc                                                     entity/SdaiSystem_classification.cc                                         
     entity/SdaiApplication_context_element.cc                                   entity/SdaiRepresentation_context.cc                                        
     entity/SdaiConnection_motion_classification.cc                              entity/SdaiShape_definition_representation.cc                               
     entity/SdaiSite_representation.cc                                           entity/SdaiClosed_shell.cc                                                  
     entity/SdaiShape_representation.cc                                          entity/SdaiApplication_protocol_definition.cc                               
     entity/SdaiB_spline_surface.cc                                              entity/SdaiProduct_related_product_category.cc                              
     entity/SdaiElbow_fitting_classification.cc                                  entity/SdaiBlank_fitting_classification.cc                                  
     entity/SdaiAction_method.cc                                                 entity/SdaiGroup_assignment.cc                                              
     entity/SdaiProduct_definition_shape.cc                                      entity/SdaiSeam_curve.cc                                                    
     entity/SdaiPiping_system.cc                                                 entity/SdaiContext_dependent_unit.cc                                        
     entity/SdaiExecuted_action.cc                                               entity/SdaiPiping_connector_classification.cc                               
     entity/SdaiProduct_definition_formation_relationship.cc                     entity/SdaiSwept_area_solid.cc                                              
     entity/SdaiEdge.cc                                                          entity/SdaiExternally_defined_item.cc                                       
     entity/SdaiProduct_material_composition_relationship.cc                     entity/SdaiMapped_item.cc                                                   
     entity/SdaiDate_assignment.cc                                               entity/SdaiSurface_of_revolution.cc                                         
     entity/SdaiVector.cc                                                        entity/SdaiCentre_of_symmetry.cc                                            
     entity/SdaiData_environment.cc                                              entity/SdaiSolid_angle_measure_with_unit.cc                                 
     entity/SdaiPerson_and_organization_assignment.cc                            entity/SdaiLine_plant_item_connection.cc                                    
     entity/SdaiRatio_unit.cc                                                    entity/SdaiDerived_unit_element.cc                                          
     entity/SdaiFlange_fitting_neck_type_classification.cc                       entity/SdaiTrimmed_curve.cc                                                 
     entity/SdaiVertex.cc                                                        entity/SdaiComposite_curve_segment.cc                                       
     entity/SdaiProduct_definition_usage.cc                                      entity/SdaiProduct_category_relationship.cc                                 
     entity/SdaiLoop.cc                                                          entity/SdaiAction_request_assignment.cc                                     
     entity/SdaiDimensional_size.cc                                              entity/SdaiOrganization.cc                                                  
     entity/SdaiDerived_unit.cc                                                  entity/SdaiElectrical_connector_classification.cc                           
     entity/SdaiShape_dimension_representation.cc                                entity/SdaiCartesian_point.cc                                               
     entity/SdaiElectric_current_measure_with_unit.cc                            entity/SdaiEllipse.cc                                                       
     entity/SdaiCatalogue.cc                                                     entity/SdaiConic.cc                                                         
     entity/SdaiSite.cc                                                          entity/SdaiEvaluated_degenerate_pcurve.cc                                   
     entity/SdaiPlane_angle_unit.cc                                              entity/SdaiThermodynamic_temperature_measure_with_unit.cc                   
     entity/SdaiCatalogue_item.cc                                                entity/SdaiShell_based_wireframe_model.cc                                   
     entity/SdaiApplication_context.cc                                           entity/SdaiElementary_surface.cc                                            
     entity/SdaiOpen_shell.cc                                                    entity/SdaiConnected_face_set.cc                                            
     entity/SdaiAxis2_placement_3d.cc                                            entity/SdaiCurve_replica.cc                                                 
     entity/SdaiDesign_project_assignment.cc                                     entity/SdaiSurface_curve.cc                                                 
     entity/SdaiCalendar_date.cc                                                 entity/SdaiDescriptive_colour.cc                                            
     entity/SdaiEdge_loop.cc                                                     entity/SdaiPlant_spatial_configuration_action_request_assignment.cc         
     entity/SdaiProduct_definition_substitute.cc                                 entity/SdaiOriented_closed_shell.cc                                         
     entity/SdaiAdvanced_csg_shape_representation.cc                             entity/SdaiPlant_item_connection.cc                                         
     entity/SdaiExternally_defined_plant_item_definition.cc                      entity/SdaiFace_bound.cc                                                    
     entity/SdaiLine_less_piping_system.cc                                       entity/SdaiPlant_spatial_configuration_person_and_organization_assignment.cc 
     entity/SdaiOrganization_role.cc                                             entity/SdaiOriented_face.cc                                                 
     entity/SdaiSolid_model.cc                                                   entity/SdaiFace.cc                                                          
     entity/SdaiProduct_definition.cc                                            entity/SdaiSwept_surface.cc                                                 
     entity/SdaiConnection_functional_classification.cc                          entity/SdaiBounded_pcurve.cc                                                
     entity/SdaiBoolean_result.cc                                                entity/SdaiRepresentation_item.cc                                           
     entity/SdaiAction_method_relationship.cc                                    entity/SdaiPlane.cc                                                         
     entity/SdaiCircle.cc                                                        entity/SdaiLine.cc                                                          
     entity/SdaiDocument.cc                                                      entity/SdaiDate_and_time_assignment.cc                                      
     entity/SdaiProduct.cc                                                       entity/SdaiPurchase_assignment.cc                                           
     entity/SdaiStructural_load_connector_classification.cc                      entity/SdaiChange_to_assignment.cc                                          
     entity/SdaiMaterial_designation_characterization.cc                         entity/SdaiPlacement.cc                                                     
     entity/SdaiPresentation_layer_assignment.cc                                 entity/SdaiB_spline_curve.cc                                                
     entity/SdaiConnector_end_type_classification.cc                             entity/SdaiDocument_relationship.cc                                         
     entity/SdaiPcurve.cc                                                        entity/SdaiVersioned_action_request.cc                                      
     entity/SdaiOrganizational_project.cc                                        entity/SdaiMeasure_with_unit.cc                                             
     entity/SdaiRectangular_trimmed_surface.cc                                   entity/SdaiCurve.cc                                                         
     entity/SdaiDate.cc                                                          entity/SdaiDefinitional_representation.cc                                   
     entity/SdaiThermodynamic_temperature_unit.cc                                entity/SdaiApproval_person_organization.cc                                  
     entity/SdaiCurve_bounded_surface.cc                                         entity/SdaiParabola.cc                                                      
     entity/SdaiPlant.cc                                                         entity/SdaiQuasi_uniform_curve.cc                                           
     entity/SdaiUniform_curve.cc                                                 entity/SdaiGeometric_curve_set.cc                                           
     entity/SdaiInline_equipment.cc                                              entity/SdaiLength_unit.cc                                                   
     entity/SdaiColour_rgb.cc                                                    entity/SdaiBoundary_curve.cc                                                
     entity/SdaiLength_measure_with_unit.cc                                      entity/SdaiProduct_category.cc                                              
     entity/SdaiRational_b_spline_surface.cc                                     entity/SdaiTime_unit.cc                                                     
     entity/SdaiCartesian_transformation_operator.cc                             entity/SdaiLocal_time.cc                                                    
     entity/SdaiExternal_source.cc                                               entity/SdaiName_assignment.cc                                               
     entity/SdaiConversion_based_unit.cc                                         entity/SdaiPiping_component_class.cc                                        
     entity/SdaiProduct_definition_formation.cc                                  entity/SdaiConnection_node.cc                                               
     entity/SdaiDimensional_exponents.cc                                         entity/SdaiLine_plant_item_branch_connection.cc                             
     entity/SdaiPiping_component_definition.cc                                   entity/SdaiProduct_context.cc                                               
     entity/SdaiAssembly_component_usage.cc                                      entity/SdaiDimensional_location.cc                                          
     entity/SdaiOffset_surface.cc                                                entity/SdaiGeometric_representation_context.cc                              
     entity/SdaiMass_measure_with_unit.cc                                        entity/SdaiPlant_item_weight_representation.cc                              
     entity/SdaiSi_unit.cc                                                       entity/SdaiInterfering_shape_element.cc                                     
     entity/SdaiSurface.cc                                                       entity/SdaiPlant_line_segment_definition.cc                                 
     entity/SdaiLine_branch_connection.cc                                        entity/SdaiPlant_item_connector.cc                                          
     entity/SdaiOffset_curve_2d.cc                                               entity/SdaiDesign_project.cc                                                
     entity/SdaiPipe_closure_fitting_classification.cc                           entity/SdaiAction_assignment.cc                                             
     entity/SdaiApproval_assignment.cc                                           entity/SdaiHalf_space_solid.cc                                              
     entity/SdaiTorus.cc                                                         entity/SdaiDocument_reference.cc                                            
     entity/SdaiReference_geometry.cc                                            entity/SdaiRevolved_face_solid.cc                                           
     entity/SdaiPlane_angle_measure_with_unit.cc                                 entity/SdaiShape_aspect.cc                                                  
     entity/SdaiVolume_measure_with_unit.cc                                      entity/SdaiBounded_curve.cc                                                 
     entity/SdaiPlant_spatial_configuration_organization_assignment.cc           entity/SdaiRatio_measure_with_unit.cc                                       
     entity/SdaiClassification_assignment.cc                                     entity/SdaiReserved_space.cc                                                
     entity/SdaiRight_circular_cylinder.cc                                       entity/SdaiAction_request_solution.cc                                       
     entity/SdaiAxis2_placement_2d.cc                                            entity/SdaiDirected_action.cc                                               
     entity/SdaiPoint.cc                                                         entity/SdaiPoint_replica.cc                                                 
     entity/SdaiCoordinated_universal_time_offset.cc                             entity/SdaiFunctionally_defined_transformation.cc                           
     entity/SdaiLine_termination_connection.cc                                   entity/SdaiDirection.cc                                                     
     entity/SdaiApproval_status.cc                                               entity/SdaiOriented_path.cc                                                 
     entity/SdaiRepresentation_map.cc                                            entity/SdaiPlant_spatial_configuration_change_assignment.cc                 
     entity/SdaiVolume_unit.cc                                                   entity/SdaiBezier_surface.cc                                                
     entity/SdaiCylindrical_surface.cc                                           entity/SdaiPerson_and_organization.cc                                       
     entity/SdaiBrep_with_voids.cc                                               entity/SdaiChange_item_id_assignment.cc                                     
     entity/SdaiStructural_system.cc                                             entity/SdaiSurface_of_linear_extrusion.cc                                   
     entity/SdaiMake_from_usage_option.cc                                        entity/SdaiPlant_spatial_configuration_document_reference.cc                
     entity/SdaiProduct_definition_context.cc                                    entity/SdaiExtruded_face_solid.cc                                           
     entity/SdaiProduct_definition_relationship.cc                               entity/SdaiSpecialty_item_classification.cc                                 
     entity/SdaiPerson_assignment.cc                                             entity/SdaiExternally_defined_classification.cc                             
     entity/SdaiReducer_fitting_classification.cc                                entity/SdaiSite_feature.cc                                                  
     entity/SdaiQualified_representation_item.cc                                 entity/SdaiApproval_date_time.cc                                            
     entity/SdaiPoint_on_curve.cc                                                entity/SdaiGeometric_representation_item.cc                                 
     entity/SdaiIntersection_curve.cc                                            entity/SdaiSwept_face_solid.cc                                              
     entity/SdaiAction_directive.cc                                              entity/SdaiCsg_solid.cc                                                     
     entity/SdaiOffset_curve_3d.cc                                               entity/SdaiSupport_constraint_representation.cc                             
     entity/SdaiArea_unit.cc                                                     entity/SdaiOriented_open_shell.cc                                           
     entity/SdaiDate_time_role.cc                                                entity/SdaiDescriptive_representation_item.cc                               
     entity/SdaiDocument_type.cc                                                 entity/SdaiProperty_definition_relationship.cc                              
     entity/SdaiInstrumentation_and_control_system.cc                            entity/SdaiAction.cc                                                        
     entity/SdaiSphere.cc                                                        entity/SdaiConical_surface.cc                                               
     entity/SdaiSpacer_fitting_classification.cc                                 entity/SdaiStream_design_case.cc                                            
     entity/SdaiRequired_material_property.cc                                    entity/SdaiEdge_curve.cc                                                    
     entity/SdaiPlant_item_route.cc                                              entity/SdaiFlange_fitting_classification.cc                                 
     entity/SdaiAngular_location.cc                                              entity/SdaiBlock.cc                                                         
     entity/SdaiNamed_unit.cc                                                    entity/SdaiCharacterized_object.cc                                          
     entity/SdaiShape_aspect_deriving_relationship.cc                            entity/SdaiColour_specification.cc                                          
     entity/SdaiExtruded_area_solid.cc                                           entity/SdaiPiping_design_csg_shape_representation.cc                        
     entity/SdaiTruncated_pyramid.cc                                                )

  set(sdai_ap227_type_impls
     type/SdaiBoolean_operator_var.cc                                            type/SdaiClassification_item.cc                                             
     type/SdaiCsg_select.cc                                                      type/SdaiPlant_spatial_configuration_person_item.cc                         
     type/SdaiAhead_or_behind_var.cc                                             type/SdaiPcurve_or_surface.cc                                               
     type/SdaiPlant_spatial_configuration_person_and_organization_item.cc        type/SdaiVector_or_direction.cc                                             
     type/SdaiChange_delta_item.cc                                               type/SdaiSource_item.cc                                                     
     type/SdaiDate_time_select.cc                                                type/SdaiPerson_organization_select.cc                                      
     type/SdaiTrimming_preference_var.cc                                         type/SdaiCurve_on_surface.cc                                                
     type/SdaiPreferred_surface_curve_representation_var.cc                      type/SdaiCsg_primitive.cc                                                   
     type/SdaiTransition_code_var.cc                                             type/SdaiGeometric_set_select.cc                                            
     type/SdaiShape_definition.cc                                                type/SdaiSi_prefix_var.cc                                                   
     type/SdaiApproval_item.cc                                                   type/SdaiPlant_spatial_configuration_date_and_time_item.cc                  
     type/SdaiPlant_spatial_configuration_action_request_item.cc                 type/SdaiDesign_project_item.cc                                             
     type/SdaiChange_item.cc                                                     type/SdaiLayered_item.cc                                                    
     type/SdaiCharacterized_product_definition.cc                                type/SdaiShell.cc                                                           
     type/SdaiPurchase_item.cc                                                   type/SdaiMeasure_value.cc                                                   
     type/SdaiSi_unit_name_var.cc                                                type/SdaiB_spline_curve_form_var.cc                                         
     type/SdaiCharacterized_definition.cc                                        type/SdaiB_spline_surface_form_var.cc                                       
     type/SdaiBoolean_operand.cc                                                 type/SdaiPlant_spatial_configuration_document_item.cc                       
     type/SdaiDimensional_characteristic.cc                                      type/SdaiAngle_relator_var.cc                                               
     type/SdaiCharacterized_material_property.cc                                 type/SdaiPlant_spatial_configuration_dated_item.cc                          
     type/SdaiSource_var.cc                                                      type/SdaiUnit.cc                                                            
     type/SdaiReversible_topology.cc                                             type/SdaiAxis2_placement.cc                                                 
     type/SdaiTrimming_select.cc                                                 type/SdaiReversible_topology_item.cc                                        
     type/SdaiPlant_spatial_configuration_organization_item.cc                   type/SdaiValue_qualifier.cc                                                 
     type/SdaiKnot_type_var.cc                                                      )
endif(SC_UNITY_BUILD)

set( sdai_ap227_misc_impls
     SdaiAll.cc    compstructs.cc    schema.cc
     SdaiPLANT_SPATIAL_CONFIGURATION.cc
     SdaiPLANT_SPATIAL_CONFIGURATION.init.cc   )

set(schema_target_files ${sdai_ap227_entity_impls} ${sdai_ap227_type_impls} ${sdai_ap227_misc_impls})
SCHEMA_TARGETS("/Users/AJ/stepcode/data/ap227/ap227.exp" "plant_spatial_configuration"
                 "${schema_target_files}")
