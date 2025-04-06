package com.volvadvit.license.utils.filter

import org.springframework.util.Assert

class UserContextHolder {

    companion object {
        private val userContext: ThreadLocal<UserContext?> = ThreadLocal<UserContext?>()

        fun getContext(): UserContext? {
            var context: UserContext? = userContext.get()

            if (context == null) {
                context = createEmptyContext()
                userContext.set(context)
            }
            return userContext.get()
        }

        fun setContext(context: UserContext?) {
            Assert.notNull(context, "Only non-null UserContext instances are permitted")
            userContext.set(context)
        }

        private fun createEmptyContext(): UserContext {
            return UserContext()
        }
    }
}