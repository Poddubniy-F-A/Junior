Для работы сначала запустить серверную часть, затем в нескольких инстансах клиентскую (активировать опцию компилятора allow multiple instances). Условием выхода из клиента является ввод Configuration.EXIT_COMMAND, сервер работает условно бесконечно (для завершения необходимо явно убить процесс)

По умолчанию сообщения отправляются в "общий чат" (рассылаются всем); для того чтобы отправить "личное сообщение", необходимо тегнуть нужного пользователя ("@<id пользователя>" в самом начале сообщения)
