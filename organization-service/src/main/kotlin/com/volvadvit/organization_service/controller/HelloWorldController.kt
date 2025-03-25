package com.volvadvit.organization_service.controller

import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(value= ["hello"])
class HelloWorldController {

    @GetMapping(value = ["/{firstName}"])
    fun helloGET(
        @PathVariable("firstName") firstName: String?,
        @RequestParam("lastName") lastName: String?
    ): String {
        return String.format(
            "{\"message\":\"Hello %s %s\"}",
            firstName, lastName
        )
    }
}