# activiti7 core

### API

Process(Task)Runtime負責操作process(task)
method argument: XXXPayload 以Process(Task)PayloadBuilder製造
所有method呼叫時需要user有ROLE_ACTIVITI_USER
(spring security context中authority包含ROLE_ACTIVITI_USER
還需要custom UserDetailsService提供user/role關聯)

access and data scope權限都由spring security機制管理

Process(Task)AdminRuntime類似legacy API, 可跳過data scope權限限制
但所有method呼叫時需要user有ROLE_ACTIVITI_ADMIN
