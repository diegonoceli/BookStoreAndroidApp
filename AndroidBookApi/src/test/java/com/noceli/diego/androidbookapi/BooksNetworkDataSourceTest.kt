package com.noceli.diego.androidbookapi

import getBookResponse
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test

@ExperimentalCoroutinesApi
class BooksNetworkDataSourceTest {
    private val mockWebServer = MockWebServer()
    private val sut = BookService(RetrofitClient().retrofit)

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `should fetch books correctly given 200 response`() {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(getBookResponse())
        )

        runBlocking {
            val actual = sut.searchBooks("ios", 5, 0).execute()
                .body()?.items?.first()?.toBook()
                ?: "Fail during fetchBook"

            val expected = Book(
                id = "mmx3CgAAQBAJ",
                description = "Inovar, empreender, criar e sonhar! Em um mundo cada vez mais conectado, inovar é sinônimo de criatividade e inteligência. Eletrônicos como celulares e dispositivos “vestíveis” já fazem parte do nosso dia a dia. Pensando nisso, nada melhor que colocar aquela sua ideia em prática: empreender criando aplicativos de sucesso. Com este livro será possível aprender como desenvolver aplicativos para dispositivos móveis, sejam celulares, tablets, relógios, óculos, etc. Inclui tópicos sobre Firemonkey, layouts responsivos, sensores, bancos de dados embarcado SQLite, câmera fotográfica, geolocalização (GPS), monetização, notificações e muito mais. Utilizando o Delphi, seus aplicativos serão desenvolvidos de forma amigável, simples e rápida. Ganhe dinheiro juntando criatividade, inovação e DELPHI! Prefácios de Marco Cantù e Claudio Nasajon",
                title = "Delphi para Android e iOS",
                authors = listOf("William Duarte"),
                buyLink = "https://play.google.com/store/books/details?id=mmx3CgAAQBAJ&rdid=book-mmx3CgAAQBAJ&rdot=1&source=gbs_api",
                imageThumbnail = "http://books.google.com/books/content?id=mmx3CgAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
            )

            assertEquals(expected, actual)
        }
    }
}