package br.com.aulaandroid.ui.newAccount

import br.com.aulaandroid.data.model.UserModel
import br.com.aulaandroid.data.repository.NewAccountRepository
import br.com.aulaandroid.util.RequestHandler
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class NewAccountViewModelTest {

    private val repository = mockk<NewAccountRepository>()
    lateinit var viewModel : NewAccountViewModel
    private val testDispatcher = StandardTestDispatcher()


    @Before
    fun setup(){
        Dispatchers.setMain(testDispatcher)
        viewModel = NewAccountViewModel(repository)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }


    //Name test
    @Test
    fun `validName deve retornar true quando o nome completo for válido`(){
        viewModel.validName("Nome Completo")
        assertTrue(viewModel.validName.value)
    }

    @Test
    fun `validName deve retornar false quando o nome completo for apenas um`(){
        viewModel.validName("Nome")
        assertFalse(viewModel.validName.value)
    }

    @Test
    fun `validName deve retornar false quando o nome completo for vazio`(){
        viewModel.validName("")
        assertFalse(viewModel.validName.value)
    }

    //Email Teste

    @Test
    fun `validEmail deve retornar true quando o email for válido`(){
        viewModel.validEmail("daniel@daniel.daniel")
        assertTrue(viewModel.validEmail.value)
    }

    @Test
    fun `validEmail deve retornar false quando o email for inválido`(){
        viewModel.validEmail("email@email")
        assertFalse(viewModel.validEmail.value)
    }

    @Test
    fun `createNewAccount deve emitir sucesso quando o usuário for criado com sucesso`(){
        val user = UserModel.getUserModel()

        coEvery { repository.newAccount(user) } returns RequestHandler.Success(Unit)

        viewModel.createNewAccount(user)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(viewModel.newAccountState.value, NewAccountState.Success)
        assertFalse(viewModel.loadingButton.value)
    }

    @Test
    fun `createNewAccount deve emitir falha quando o cadastro falhar`(){
        val user = UserModel.getUserModel()
        val exception = Exception("Falha no cadastro")

        coEvery { repository.newAccount(user) } returns RequestHandler.Failure(exception)

        viewModel.createNewAccount(user)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(viewModel.newAccountState.value, NewAccountState.Failure(exception))
        assertFalse(viewModel.loadingButton.value)
    }

}