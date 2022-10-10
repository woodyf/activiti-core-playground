--USERS
insert into ccafs_user (ID, EMP_NO, IS_ACTIVE, IS_ENABLE, MGMT_TYPE, REVIEW_STATUS) values ('896eed9a-4b31-4d66-ade7-f7f7c293e9a8', 'jack', 'Y', 'Y', 'ADD', 'APPROVED');
insert into ccafs_user (ID, EMP_NO, IS_ACTIVE, IS_ENABLE, MGMT_TYPE, REVIEW_STATUS) values ('242ac117-5f5f-4d46-a260-6f9f333b5272', 'abby', 'Y', 'Y', 'ADD', 'APPROVED');
insert into ccafs_user (ID, EMP_NO, IS_ACTIVE, IS_ENABLE, MGMT_TYPE, REVIEW_STATUS) values ('ff081798-0436-43ed-93ea-96da8887f889', 'tommy', 'Y', 'Y', 'ADD', 'APPROVED');
insert into ccafs_user (ID, EMP_NO, IS_ACTIVE, IS_ENABLE, MGMT_TYPE, REVIEW_STATUS) values ('bd8a5a3a-a417-4380-a4be-28518be40507', 'gina', 'Y', 'Y', 'ADD', 'APPROVED');

--ROLES
  --用ROLE_打頭的是角色, ROLE_ACTIVITI_USER可以打一般的runtime api, ROLE_ACTIVITI_ADMIN可以打admin的api
insert into ccafs_role (ID, ROLE_CODE) values ('7be93b06-a246-4532-a70e-e69582f6db8f', 'ROLE_ACTIVITI_USER');
insert into ccafs_role (ID, ROLE_CODE) values ('ee9cfb92-a950-41c1-976d-7f5c8d473ca4', 'ROLE_ACTIVITI_ADMIN');
  --用GROUP_打頭的是candidate group
insert into ccafs_role (ID, ROLE_CODE) values ('990feee8-e315-4e9b-94ad-b0498d5a385c', 'GROUP_team0');

--USER_ROLES
--jack什麼都沒有
--abby是activiti user
insert into ccafs_user_role (ROLE_ID, USER_ID) values ('7be93b06-a246-4532-a70e-e69582f6db8f', '242ac117-5f5f-4d46-a260-6f9f333b5272');
--tommy是activiti user, 在team0這個group中
insert into ccafs_user_role (ROLE_ID, USER_ID) values ('7be93b06-a246-4532-a70e-e69582f6db8f', 'ff081798-0436-43ed-93ea-96da8887f889');
insert into ccafs_user_role (ROLE_ID, USER_ID) values ('990feee8-e315-4e9b-94ad-b0498d5a385c', 'ff081798-0436-43ed-93ea-96da8887f889');
--gina是activiti admin, 但沒有所屬group
insert into ccafs_user_role (ROLE_ID, USER_ID) values ('7be93b06-a246-4532-a70e-e69582f6db8f', 'bd8a5a3a-a417-4380-a4be-28518be40507');
insert into ccafs_user_role (ROLE_ID, USER_ID) values ('ee9cfb92-a950-41c1-976d-7f5c8d473ca4', 'bd8a5a3a-a417-4380-a4be-28518be40507');